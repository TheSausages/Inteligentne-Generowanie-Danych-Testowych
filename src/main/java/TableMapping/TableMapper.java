package TableMapping;

import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
import Exceptions.DataException;
import TableMapping.Fields.Field;
import TableMapping.Fields.NumberField;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class TableMapper {

    public List<TableMappingClass> mapMySqlTable(List<ResultSet> tablesInformation, SupportedDatabases type) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach(tableInfo -> {
            try {
                while (tableInfo.next()) {
                    TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                    currentTable
                            .tableName(tableInfo.getString(1))
                            .tableType(type);

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

        return mappedDatabase;
    }

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
                    System.out.println(Arrays.toString(words));

                    if (words[i].matches("DEFAULT_.+")) {
                        columnBuilder.defaultValue(words[i].substring(8));
                    }
                }
            }
        }

        return columnBuilder.build();
    }

    private Field findFieldMySQL(String word) {
        String[] elements = word.split("[,()]");

        if (elements[0].equals("tinyint") && elements[1].equals("1")) {
            elements[0] = "tinyint1";
        }

        Field field = Field.findFieldType(elements[0]);

        field.setFieldInfo(elements);

        if (word.contains("unsigned") && field instanceof NumberField) {
            ((NumberField) field).setUnsigned(true);
            ((NumberField) field).removeUnsignedFromName();
        }

        return field;
    }

    public List<TableMappingClass> mapSQLServerTable(Map<ResultSet, ResultSet> tablesInformation, SupportedDatabases type) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                if (tableInfo.first()) {
                    currentTable
                            .tableName(tableInfo.getString(3))
                            .tableType(type)
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

        return mappedDatabase;
    }

    private ColumnMappingClass mapColumnSQLServer(String columnName, String defaultValue, String isNullable, String dataType, String maxLength, String precision, String autoIncrement) {
        ColumnMappingClass.ColumnBuilder columnBuilder = new ColumnMappingClass.ColumnBuilder()
                .name(columnName)
                .defaultValue(defaultValue);

        columnBuilder.field(findFieldSQLServer(dataType, maxLength, precision));

        if (isNullable.equals("NO")) columnBuilder.notNullable();

        if (autoIncrement.equals("1")) columnBuilder.isAutoIncrement();

        return columnBuilder.build();
    }

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

    public List<TableMappingClass> mapOracleTable(Map<ResultSet, ResultSet> tablesInformation, SupportedDatabases type) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                if (tableInfo.first()) {
                    currentTable
                            .tableName(tableInfo.getString(1))
                            .tableType(type)
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

        return mappedDatabase;
    }


    private ColumnMappingClass mapColumnOracle(String columnName, String defaultValue, String isNullable, String dataType,String maxLength, String range, String precision, String autoIncrement) {
        ColumnMappingClass.ColumnBuilder columnBuilder = new ColumnMappingClass.ColumnBuilder()
                .name(columnName)
                .defaultValue(defaultValue == null ? null : defaultValue.substring(1, defaultValue.length() - 1));

        columnBuilder.field(findFieldOracle(dataType, maxLength, range, precision));

        if (isNullable.equals("N")) columnBuilder.notNullable();

        if (autoIncrement.equals("YES")) columnBuilder.isAutoIncrement();

        return columnBuilder.build();
    }

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
}
