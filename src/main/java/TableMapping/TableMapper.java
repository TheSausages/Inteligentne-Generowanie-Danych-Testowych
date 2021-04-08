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

/**
 * Class that contains mapping methods for all databases supported by the application
 */
public class TableMapper {
    DatabaseInfo databaseInfo;

    public TableMapper(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    /**
     * Method that maps MySQL database tables
     * @param tablesInformation A ResultSet List containing information about tables from the selected MySQL database
     * @return Returns a {@link TableMappingClass} containing information
     */
    public List<TableMappingClass> mapMySqlTable(List<ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach(tableInfo -> {
            try {
                while (tableInfo.next()) {
                    TableMappingClass.TableBuilder currentTable = TableMappingClass.builder()
                            .tableName(tableInfo.getString(1))
                            .tableType(databaseInfo.getDatabaseDrivers());

                    String[] lines = tableInfo.getString(2).split("\n");

                    if (lines.length < 1) {
                        throw new DataException("No Data on columns in table " + tableInfo.getString(1));
                    }

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

    /**
     * Method that maps a column (in a form of String from the 'SHOW CREATE TABLE' sql method) from an MySQL Table
     * @param line One line from the 'SHOW CREATE TABLE' sql method that contains column information
     */
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

    /**
     * Method that finds information about a column field (ex. Decimal(6,3)). Works for MySQL database
     * @param word A string that contains information about a column field
     */
    private Field findFieldMySQL(String word) {
        String[] elements = word.split("[,()]");

        Field field = Field.findFieldType(elements[0]);

        if (word.contains("unsigned") && field instanceof NumberField) {
            ((NumberField) field).setUnsigned(true);
        }

        field.setFieldInfo(elements);

        return field;
    }

    /*public List<TableMappingClass> mapSQLServerTable(Map<ResultSet, ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                while (tableInfo.next()) {
                    currentTable.tableName(tableInfo.getString(3))
                            .tableType(databaseInfo.getDatabaseDrivers())
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
        Field field = new Field();
        field.setSqlType(dataType);
        field.setMaxSize(Integer.parseInt(maxLength));
        field.setPrecision(Integer.parseInt(precision));

        return field;
    }

    public List<TableMappingClass> mapOracleTable(Map<ResultSet, ResultSet> tablesInformation) {
        List<TableMappingClass> mappedDatabase = new ArrayList<>();

        tablesInformation.forEach((tableInfo, tableConstraints) -> {
            try {
                TableMappingClass.TableBuilder currentTable = TableMappingClass.builder();

                while(tableInfo.next()) {
                    System.out.println(tableInfo.getString(1)); //nazwa tabeli
                    System.out.println(tableInfo.getString(2)); //nazwa columny
                    System.out.println(tableInfo.getString(3)); //rodzaj zmiennej
                    System.out.println(tableInfo.getString(4)); //max długość (czyli maksymalna długość dla tej zmiennej)
                    System.out.println(tableInfo.getString(5)); //precyzja (tutaj max ilość znaków)
                    System.out.println(tableInfo.getString(6)); //skala (ile po przecinku)
                    System.out.println(tableInfo.getString(7)); //czy nullable
                    System.out.println(tableInfo.getString(8)); //default
                    System.out.println(tableInfo.getString(9)); //czy identity

                    System.out.println("-");
                }

                while (tableInfo.next()) {
                    currentTable.tableName(tableInfo.getString(1))
                            .tableType(databaseInfo.getDatabaseDrivers())
                            .addColumn(mapColumnOracle(tableInfo.getString(2), tableInfo.getString(8),
                                    tableInfo.getString(7), tableInfo.getString(3), tableInfo.getString(5), tableInfo.getString(6), tableInfo.getString(9)));
                    break;
                }

                while (tableInfo.next()) {
                    currentTable.addColumn(mapColumnOracle(tableInfo.getString(2), tableInfo.getString(8),
                            tableInfo.getString(7), tableInfo.getString(3), tableInfo.getString(5), tableInfo.getString(6), tableInfo.getString(9)));
                }

                while (tableConstraints.next()) {
                    System.out.println(tableConstraints.getString(1)); //tabela
                    System.out.println(tableConstraints.getString(2)); //rodzaj https://docs.oracle.com/cd/B19306_01/server.102/b14237/statviews_1037.htm#i1576022
                    System.out.println(tableConstraints.getString(3)); //nazwa kolumny
                    System.out.println(tableConstraints.getString(4)); //nazwa constraint
                    System.out.println(tableConstraints.getString(5)); //nazwa tabeli LUB jesli klucz obcy to nazwa tabeli do któej referencja
                    System.out.println(tableConstraints.getString(6)); //nazwa tabeli LUB jesli klucz obcy to nazwa kolumny do któej referencja
                    System.out.println("-");
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

        //columnBuilder.field(findFieldOracle(dataType, maxLength, precision));

        return columnBuilder.build();
    }

    private Field findFieldOracle(String dataType, String maxLength, String precision) {
        Field field = new Field();
        field.setSqlType(dataType);
        field.setMaxSize(Integer.parseInt(maxLength == null ? "-1" : maxLength));
        field.setPrecision(Integer.parseInt(precision == null ? "0" : precision));

        return field;
    }*/
}
