package TableMapping;

import DatabaseConnection.DatabaseInfo;
import Exceptions.ConnectionException;
import Exceptions.DataException;
import TableMapping.Fields.Field;
import TableMapping.Fields.NumberField;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TableMapper {
    DatabaseInfo databaseInfo;

    public TableMapper(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public List<TableMappingClass> mapMySqlTable(List<ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach(tableInfo -> {
            try {
                while (tableInfo.next()) {
                    TableMappingClass.TableBuilder currentTable = TableMappingClass.builder()
                            .tableName(tableInfo.getString(1))
                            .tableType(databaseInfo.getSupportedDatabase());

                    String[] lines = tableInfo.getString(2).split("\n");

                    if (lines.length < 1) {
                        throw new DataException("No Data on columns in table " + tableInfo.getString(1));
                    }

                    System.out.println(Arrays.toString(lines));

                    for (int lineIndex = 1; lineIndex < lines.length - 1; lineIndex++) {
                        if (lines[lineIndex].contains("PRIMARY KEY")) {
                            String columnName = lines[lineIndex].substring(lines[lineIndex].indexOf("`") + 1, lines[lineIndex].lastIndexOf("`"));

                            currentTable.streamColumns()
                                    .filter(columnMappingClass -> columnName.equals(columnMappingClass.getName()))
                                    .findFirst().get().setPrimaryKey(true);
                            continue;
                        }

                        if (lines[lineIndex].contains("FOREIGN KEY")) {
                            String[] names = lines[lineIndex].split("`");

                            currentTable.streamColumns()
                                    .filter(columnMappingClass -> names[3].equals(columnMappingClass.getName()))
                                    .findFirst().get().getForeignKey().foreignKeyInfo(names[7], names[5]);
                            continue;
                        }

                        if (lines[lineIndex].contains("UNIQUE KEY")) {
                            String[] names = lines[lineIndex].split("`");

                            currentTable.streamColumns()
                                    .filter(columnMappingClass -> names[3].equals(columnMappingClass.getName()))
                                    .findFirst().get().setUnique(true);
                            continue;
                        }

                        if (lines[lineIndex].contains("KEY `")) {
                            continue;
                        }

                        lines[lineIndex] = lines[lineIndex]
                                .replace(" unsigned", "_unsigned")
                                .replace("NOT NULL", "NOT_NULL")
                                .replace("DEFAULT ", "DEFAULT_");

                        currentTable.addColumn(mapColumnMySQL(lines[lineIndex]));
                    }

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

        String[] words = ArrayUtils.removeAllOccurrences(line.split(" "), "");

        if (words.length == 0) {
            throw new DataException("Not information on Column received");
        }

        if (words.length < 2) {
            if (words[0].matches("`.+`")) {
                throw new DataException("Not enough information on column " + words[0].substring(1, words[0].length() - 1));
            } else {
                throw new DataException("Not information besides Type on Column received");
            }
        }

        columnBuilder.name(words[0].substring(1, words[0].length() - 1));
        columnBuilder.field(findFieldMySQL(words[1]));

        for (int i = 2; i < words.length; i++) {

            if (words[i].charAt(words[i].length() - 1) == ',') {
                words[i] = StringUtils.chop(words[i]);
            }

            switch (words[i]) {
                case "NOT_NULL" -> columnBuilder.notNullable();
                case "UNIQUE" -> columnBuilder.isUnique();
                case "AUTO_INCREMENT" -> columnBuilder.isAutoIncrement();
                default -> {
                    if (words[i].matches("DEFAULT_.+")) {
                        columnBuilder.defaultValue(words[i].substring(words[i].indexOf("_") + 1).replace("'", ""));
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

        if (word.contains("unsigned") && field instanceof NumberField) {
            ((NumberField) field).setUnsigned(true);
        }

        field.setFieldInfo(elements);

        return field;
    }

    public List<TableMappingClass> mapSQLServerTable(Map<ResultSet, ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                while (tableInfo.next()) {
                    currentTable.tableName(tableInfo.getString(3))
                            .tableType(databaseInfo.getSupportedDatabase())
                            .addColumn(mapColumnSQLServer(tableInfo.getString(4), tableInfo.getString(5),
                            tableInfo.getString(6), tableInfo.getString(7), tableInfo.getString(8), tableInfo.getString(9), tableInfo.getString(10)));
                    break;
                }

                while (tableInfo.next()) {
                    currentTable.addColumn(mapColumnSQLServer(tableInfo.getString(4), tableInfo.getString(5),
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
            } catch (SQLException e) {
                throw new ConnectionException("There is a problem mapping a table: " + e.getMessage());
            }
        });

        return mappedDatabase;
    }

    private ColumnMappingClass mapColumnSQLServer(String columnName, String defaultValue, String isNullable, String dataType, String maxLength, String precision, String autoIncrement) {
        ColumnMappingClass.ColumnBuilder columnBuilder = new ColumnMappingClass.ColumnBuilder()
                .name(columnName)
                .defaultValue(defaultValue);

        if (isNullable.equals("NO")) columnBuilder.notNullable();

        if (autoIncrement.equals("1")) columnBuilder.isAutoIncrement();

        columnBuilder.field(findFieldSQLServer(dataType, maxLength, precision));

        return columnBuilder.build();
    }

    private Field findFieldSQLServer(String dataType, String maxLength, String precision) {
        switch (dataType) {
            case "Text" -> dataType = "TextServer";
            case "Binary" -> dataType = "BinaryServer";
            case "Bit" -> dataType = "BitServer";
            case "Datetime" -> dataType = "DatetimeServer";
        }

        Field field = Field.findFieldType(dataType);

        field.setFieldInfo(new String[]{dataType, maxLength, precision});

        return field;
    }

    public List<TableMappingClass> mapOracleTable(Map<ResultSet, ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                while (tableInfo.next()) {
                    currentTable.tableName(tableInfo.getString(1))
                            .tableType(databaseInfo.getSupportedDatabase())
                            .addColumn(mapColumnOracle(tableInfo.getString(2), tableInfo.getString(8),
                                    tableInfo.getString(7), tableInfo.getString(3), tableInfo.getString(5), tableInfo.getString(6), tableInfo.getString(9)));
                    break;
                }

                while (tableInfo.next()) {
                    currentTable.addColumn(mapColumnOracle(tableInfo.getString(2), tableInfo.getString(8),
                            tableInfo.getString(7), tableInfo.getString(3), tableInfo.getString(5), tableInfo.getString(6), tableInfo.getString(9)));
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

            } catch (SQLException e) {
                throw new ConnectionException("There is a problem mapping a table: " + e.getMessage());
            }
        });

        return mappedDatabase;
    }


    private ColumnMappingClass mapColumnOracle(String columnName, String defaultValue, String isNullable, String dataType, String maxLength, String precision, String autoIncrement) {
        ColumnMappingClass.ColumnBuilder columnBuilder = new ColumnMappingClass.ColumnBuilder()
                .name(columnName)
                .defaultValue(defaultValue);

        if (isNullable.equals("N")) columnBuilder.notNullable();

        if (autoIncrement.equals("YES")) columnBuilder.isAutoIncrement();

        columnBuilder.field(findFieldOracle(dataType, maxLength, precision));

        return columnBuilder.build();
    }

    private Field findFieldOracle(String dataType, String maxLength, String precision) {
        switch (dataType) {
            case "Date" -> dataType = "DateOracle";
            case "Blob" -> dataType = "BlobOracle";
        }

        Field field = Field.findFieldType(StringUtils.capitalize(dataType.toLowerCase()));

        field.setFieldInfo(new String[]{dataType, maxLength == null ? "-1" : maxLength, precision == null ? "0" : precision});

        return field;
    }
}
