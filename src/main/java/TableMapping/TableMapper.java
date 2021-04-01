package TableMapping;

import DatabaseConnection.DatabaseInfo;
import Exceptions.ConnectionException;
import Exceptions.DataException;

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

        System.out.println("1");

        tablesInformation.forEach(tableInfo -> {
            try {
                while (tableInfo.next()) {
                    TableMappingClass mappedTable = new TableMappingClass(tableInfo.getString(1), databaseInfo.getDatabaseDrivers());

                    String[] lines = tableInfo.getString(2).split("\n");

                    if (lines.length < 1) {
                        throw new DataException("No Data on columns in table " + tableInfo.getString(1));
                    }

                    for (int lineIndex = 1; lineIndex < lines.length - 1; lineIndex++) {
                        if (lines[lineIndex].contains("PRIMARY KEY")) {
                            String columnName = lines[lineIndex].substring(lines[lineIndex].indexOf("`") + 1, lines[lineIndex].lastIndexOf("`"));

                            mappedTable.getColumns().stream()
                                    .filter(columnMappingClass -> columnName.equals(columnMappingClass.getName()))
                                    .findFirst().get().setPrimaryKey(true);
                            continue;
                        }

                        if (lines[lineIndex].contains("FOREIGN KEY")) {
                            String[] names = lines[lineIndex].split("`");

                            mappedTable.getColumns().stream()
                                    .filter(columnMappingClass -> names[3].equals(columnMappingClass.getName()))
                                    .findFirst().get().getForeignKey().foreignKeyInfo(names[7], names[5]);
                            continue;
                        }

                        if (lines[lineIndex].contains("KEY `")) {
                            continue;
                        }

                        ColumnMappingClass column = new ColumnMappingClass();

                        lines[lineIndex] = lines[lineIndex]
                                .replace(" unsigned", "-unsigned")
                                .replace("NOT NULL", "NOT-NULL")
                                .replace("DEFAULT ", "DEFAULT-");

                        column.mapColumnsMySQL(lines[lineIndex]);

                        mappedTable.addColumn(column);
                    }

                    mappedDatabase.add(mappedTable);
                }
            } catch (SQLException e) {
                throw new ConnectionException("There is a problem mapping a table: " + e.getMessage());
            }

        });

        return mappedDatabase;
    }
}
