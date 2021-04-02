package TableMapping;

import DatabaseConnection.DatabaseInfo;
import Exceptions.ConnectionException;
import Exceptions.DataException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

                        if (lines[lineIndex].contains("KEY `")) {
                            continue;
                        }

                        lines[lineIndex] = lines[lineIndex]
                                .replace(" unsigned", "-unsigned")
                                .replace("NOT NULL", "NOT-NULL")
                                .replace("DEFAULT ", "DEFAULT-");

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
    public ColumnMappingClass mapColumnMySQL(String line) {
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
        columnBuilder.field(findField(words[1]));

        for (int i = 2; i < words.length; i++) {

            if (words[i].charAt(words[i].length() - 1) == ',') {
                words[i] = StringUtils.chop(words[i]);
            }

            switch (words[i]) {
                case "NOT-NULL" -> columnBuilder.notNullable();
                case "UNIQUE" -> columnBuilder.isUnique();
                case "AUTO_INCREMENT" -> columnBuilder.isAutoIncrement();
                default -> {
                    if (words[i].matches("DEFAULT-.+")) {
                        columnBuilder.defaultValue(words[i].substring(words[i].indexOf("-") + 1).replace("'", ""));
                    }
                }
            }
        }

        return columnBuilder.build();
    }

    /**
     * Method that finds information about a column field (ex. Decimal(6,3)). Works for databases: MySQL
     * @param word A string that contains information about a column field
     */
    private Field findField(String word) {
        Field field = new Field();

        if (word.contains("unsigned")) {
            field.setUnsigned(true);
            word = word.replace("-unsigned", "");
        }

        String[] elements = word.split("[,()]");
        switch (elements.length) {
            case 2 -> {
                field.setSqlType(elements[0]);
                field.setMaxSize(Integer.parseInt(elements[1]));
            }

            case 1 -> {
                field.setSqlType(elements[0]);
            }

            case 3 -> {
                field.setSqlType(elements[0]);
                field.setMaxSize(Integer.parseInt(elements[1]));
                field.setPrecision(Integer.parseInt(elements[2]));
            }
        }

        return field;
    }
}
