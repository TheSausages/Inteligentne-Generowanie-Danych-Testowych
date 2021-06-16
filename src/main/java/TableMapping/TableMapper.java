package TableMapping;

import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
import TableMapping.Fields.Field;
import TableMapping.Fields.NumberField;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class that maps the Structure of tables in the database to their representation in {@link TableMappingClass}. Each Database type has its own mapping method
 */
@NoArgsConstructor
public class TableMapper {

    /**
     * Method that maps tables from a MySQL database into its {@link TableMappingClass} representation
     * @param tablesInformation List of {@link ResultSet} that posses information about the tables inside the database
     * @return List of {@link TableMappingClass} that posses information about the tables from the database recieved from {@param tablesInformation }
     */
    public List<TableMappingClass> mapMySqlTable(List<ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach(tableInfo -> {
            try {
                while (tableInfo.next()) {
                    TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                    currentTable
                            .tableName(tableInfo.getString(1))
                            .tableType(SupportedDatabases.MYSQL);

                    String[] lines = tableInfo.getString(2).split("\n");

                    Arrays.stream(lines)
                            .skip(1)
                            .limit(lines.length - 2)
                            .forEach(line -> {
                                if (line.contains("PRIMARY KEY")) {
                                    String columnName = line.substring(16, line.lastIndexOf('`'));

                                    currentTable.streamColumns()
                                            .filter(columnMappingClass -> columnName.equals(columnMappingClass.getName()))
                                            .findFirst().get().setPrimaryKey(true);
                                    return;
                                }

                                if (line.contains("FOREIGN KEY")) {
                                    String[] names = line.split("`");

                                    currentTable.streamColumns()
                                            .filter(columnMappingClass -> names[3].equals(columnMappingClass.getName()))
                                            .findFirst().get().getForeignKey().foreignKeyInfo(names[7], names[5]);
                                    return;
                                }

                                if (line.contains("UNIQUE KEY")) {
                                    String columnName = line.split("`")[3];

                                    currentTable.streamColumns()
                                            .filter(columnMappingClass -> columnName.equals(columnMappingClass.getName()))
                                            .findFirst().get().setUnique(true);
                                    return;
                                }

                                if (line.contains("KEY `")) {
                                    return;
                                }

                                currentTable.addColumn(mapColumnMySQL(line
                                        .replace(" unsigned", "_unsigned")
                                        .replace("NOT NULL", "NOT_NULL")
                                        .replace("DEFAULT ", "DEFAULT_")));
                    });

                    mappedDatabase.add(currentTable.build());
                }
            } catch (SQLException e) {
                throw new ConnectionException("There is a problem mapping a table: " + e.getMessage());
            }

        });

        sortListByForeignKeys(mappedDatabase);

        return mappedDatabase;
    }

    /**
     * Method that maps a column from an MySQL table
     * @param line {@link String} line that represents a column from a given table
     * @return A representation of the input column in a form of {@link ColumnMappingClass}
     */
    private ColumnMappingClass mapColumnMySQL(String line) {
        ColumnMappingClass.ColumnBuilder columnBuilder = ColumnMappingClass.builder();
        String[] words = ArrayUtils.removeAllOccurrences(StringUtils.removeEnd(line, ",").split(" "), "");

        columnBuilder.name(words[0].substring(1, words[0].length() - 1));
        columnBuilder.field(findFieldMySQL(words[1]));

        for (int i = 2; i < words.length; i++) {
            switch (words[i]) {
                case "NOT_NULL" -> columnBuilder.notNullable();
                case "UNIQUE" -> columnBuilder.isUnique();
                case "AUTO_INCREMENT" -> columnBuilder.isAutoIncrement();
                default -> {
                    if (words[i].matches("DEFAULT_.+")) {
                        String defaultValue = words[i].split("_")[1];
                        columnBuilder.defaultValue(defaultValue.equals("NULL") ? "NULL" : StringUtils.chop(words[i].substring(9)));
                    }
                }
            }
        }

        return columnBuilder.build();
    }

    /**
     * Method that maps a field type from a MySQL column into its representation
     * @param word A {@link String} that is the field in an MySQL database
     * @return A class representation that extends {@link Field}. Example of such class is {@link NumberField}
     */
    private Field findFieldMySQL(String word) {
        String[] elements = word.split("[,()]");

        if (elements[0].equals("tinyint") && elements[1].equals("1")) {
            elements[0] = "tinyint1";
        }

        Field field = Field.findFieldType(elements[0]);

        field.setFieldInfo(elements);

        if (word.contains("unsigned") && field instanceof NumberField) {
            ((NumberField) field).setUnsigned(true);
        }

        return field;
    }

    /**
     * Method that maps tables from a SQl Server database into its {@link TableMappingClass} representation
     * @param tablesInformation map of {@link ResultSet} that posses information about the tables inside the database and their constrains
     * @return List of {@link TableMappingClass} that posses information about the tables from the database received from {@param tablesInformation }
     */
    public List<TableMappingClass> mapSQLServerTable(Map<ResultSet, ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                if (tableInfo.first()) {
                    currentTable
                            .tableName(tableInfo.getString(3))
                            .tableType(SupportedDatabases.SQLSERVER)
                            .addColumn(mapColumnSQLServer(tableInfo.getString(4), tableInfo.getString(5),
                                    tableInfo.getString(6), tableInfo.getString(7), tableInfo.getString(8), tableInfo.getString(9), tableInfo.getString(10)));
                }

                while (tableInfo.next()) {
                    currentTable
                            .addColumn(mapColumnSQLServer(tableInfo.getString(4), tableInfo.getString(5),
                            tableInfo.getString(6), tableInfo.getString(7), tableInfo.getString(8), tableInfo.getString(9), tableInfo.getString(10)));
                }

                while (tableConstraints.next()) {
                    String referencingColumn = tableConstraints.getString(5);

                    switch (tableConstraints.getString(3)) {
                        case "Primary key" -> currentTable.streamColumns()
                                .filter(columnMappingClass -> referencingColumn.equals(columnMappingClass.getName()))
                                .findFirst().get().setPrimaryKey(true);
                        case "Unique constraint" -> currentTable.streamColumns()
                                .filter(columnMappingClass -> referencingColumn.equals(columnMappingClass.getName()))
                                .findFirst().get().setUnique(true);
                        case "Foreign key" -> {
                            String[] referencedInfo = tableConstraints.getString(4).split(",");

                            currentTable.streamColumns()
                                    .filter(columnMappingClass -> referencingColumn.equals(columnMappingClass.getName()))
                                    .findFirst().get().getForeignKey().foreignKeyInfo(referencedInfo[1], referencedInfo[0]);
                        }
                    }
                }

                mappedDatabase.add(currentTable.build());
            } catch (Exception e) {
                throw new ConnectionException("There is a problem mapping a table: " + e.getMessage());
            }
        });

        sortListByForeignKeys(mappedDatabase);

        return mappedDatabase;
    }

    /**
     * Method that maps a field type from a SQL Server column into its representation
     * @param columnName Name of the column
     * @param defaultValue Default value of the column
     * @param isNullable Can the column take a null value
     * @param dataType Type of SQL data in SQL Server
     * @param maxLength Max length of the data (how many symbols)
     * @param precision Precision of the data
     * @param autoIncrement is the data autoincrement
     * @return A class representation that extends {@link Field}. Example of such class is {@link NumberField}
     */
    private ColumnMappingClass mapColumnSQLServer(String columnName, String defaultValue, String isNullable, String dataType, String maxLength, String precision, String autoIncrement) {
        ColumnMappingClass.ColumnBuilder columnBuilder = new ColumnMappingClass.ColumnBuilder()
                .name(columnName)
                .defaultValue(defaultValue);

        columnBuilder.field(findFieldSQLServer(dataType, maxLength, precision));

        if (isNullable.equals("NO")) columnBuilder.notNullable();

        if (autoIncrement.equals("1")) columnBuilder.isAutoIncrement();

        return columnBuilder.build();
    }

    /**
     * Method that maps a field type from a SQl Server column into its representation
     * @param dataType Type of SQL data in SQL Server
     * @param maxLength Max length of the data (how many symbols)
     * @param precision Precision of the data
     * @return A class representation that extends {@link Field}. Example of such class is {@link NumberField}
     */
    private Field findFieldSQLServer(String dataType, String maxLength, String precision) {
        Field field = switch (dataType) {
            case "Text" -> Field.findFieldType("TextServer");
            case "Binary" -> Field.findFieldType("BinaryServer");
            case "Bit" -> Field.findFieldType("BitServer");
            case "Datetime" -> Field.findFieldType("DatetimeServer");
            default -> Field.findFieldType(dataType);
        };

        field.setFieldInfo(new String[]{dataType, maxLength, precision});

        return field;
    }

    /**
     * Method that maps tables from a Oracle database into its {@link TableMappingClass} representation
     * @param tablesInformation map of {@link ResultSet} that posses information about the tables inside the database and their constrains
     * @return List of {@link TableMappingClass} that posses information about the tables from the database received from {@param tablesInformation }
     */
    public List<TableMappingClass> mapOracleTable(Map<ResultSet, ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                if (tableInfo.first()) {
                    currentTable
                            .tableName(tableInfo.getString(1))
                            .tableType(SupportedDatabases.ORACLE)
                            .addColumn(mapColumnOracle(tableInfo.getString(2), tableInfo.getString(8),
                                    tableInfo.getString(7), tableInfo.getString(3), tableInfo.getString(4),
                                    tableInfo.getString(5), tableInfo.getString(6), tableInfo.getString(9)));
                }

                while (tableInfo.next()) {
                    currentTable.addColumn(mapColumnOracle(tableInfo.getString(2), tableInfo.getString(8),
                            tableInfo.getString(7), tableInfo.getString(3), tableInfo.getString(4),
                            tableInfo.getString(5), tableInfo.getString(6), tableInfo.getString(9)));
                }

                while (tableConstraints.next()) {
                    String referencingColumn = tableConstraints.getString(3);

                    switch (tableConstraints.getString(2)) {
                        case "P" -> currentTable.streamColumns()
                                .filter(columnMappingClass -> referencingColumn.equals(columnMappingClass.getName()))
                                .findFirst().get().setPrimaryKey(true);
                        case "U" -> currentTable.streamColumns()
                                .filter(columnMappingClass -> referencingColumn.equals(columnMappingClass.getName()))
                                .findFirst().get().setUnique(true);
                        case "R" -> {
                            currentTable.streamColumns()
                                    .filter(columnMappingClass -> referencingColumn.equals(columnMappingClass.getName()))
                                    .findFirst().get().getForeignKey().foreignKeyInfo(tableConstraints.getString(6), tableConstraints.getString(5));
                        }
                    }
                }

                mappedDatabase.add(currentTable.build());

            } catch (Exception e) {
                throw new ConnectionException("There is a problem mapping a table: " + e.getMessage());
            }
        });

        sortListByForeignKeys(mappedDatabase);

        return mappedDatabase;
    }

    /**
     * Method that maps a field type from a oracle column into its representation
     * @param columnName Name of the column
     * @param defaultValue Default value of the column
     * @param isNullable Can the column take a null value
     * @param dataType Type of SQL data in Oracle
     * @param maxLength Max length of the data (how many symbols)
     * @param precision Precision of the data
     * @param autoIncrement is the data autoincrement
     * @return A class representation that extends {@link Field}. Example of such class is {@link NumberField}
     */
    private ColumnMappingClass mapColumnOracle(String columnName, String defaultValue, String isNullable, String dataType,String maxLength, String range, String precision, String autoIncrement) {
        ColumnMappingClass.ColumnBuilder columnBuilder = new ColumnMappingClass.ColumnBuilder()
                .name(columnName)
                .defaultValue(defaultValue == null ? null : defaultValue.substring(1, defaultValue.length() - 1));

        columnBuilder.field(findFieldOracle(dataType, maxLength, range, precision));

        if (isNullable.equals("N")) columnBuilder.notNullable();

        if (autoIncrement.equals("YES")) columnBuilder.isAutoIncrement();

        return columnBuilder.build();
    }

    /**
     * Method that maps a field type from a Oracle column into its representation
     * @param dataType Type of SQL data in SQL Server
     * @param maxLength Max length of the data (how many symbols)
     * @param range number of digits after the decimal point
     * @param precision Precision of the data
     * @return A class representation that extends {@link Field}. Example of such class is {@link NumberField}
     */
    private Field findFieldOracle(String dataType, String maxLength, String range, String precision) {

        switch (dataType) {
            case "DATE" -> dataType = "DateOracle";
            case "BLOB" -> dataType = "BlobOracle";
            default -> dataType = StringUtils.capitalize(dataType.toLowerCase());
        }

        Field field = Field.findFieldType(dataType);

        if (field instanceof NumberField) {
            field.setFieldInfo(new String[]{dataType, range, precision});
        } else {
            field.setFieldInfo(new String[]{dataType, maxLength, precision});
        }

        return field;
    }

    /**
     * Method that sorts the list of tables depending on the number of {@link ForeignKeyMapping}
     * @param tables
     */
    private void sortListByForeignKeys(List<TableMappingClass> tables) {
        tables.sort(Comparator.comparing(TableMappingClass::getNumberOfForeignKeys));
    }
}
