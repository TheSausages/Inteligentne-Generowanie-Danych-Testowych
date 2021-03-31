package TableMapping;

import DatabaseConnection.DatabaseInfo;
import Exceptions.DataException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMapper {
    DatabaseInfo databaseInfo;

    public DatabaseMapper(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public TableMappingClass mapMySqlTable(ResultSet tableInfo, String tableName) throws SQLException {
        TableMappingClass mappedTable = new TableMappingClass(tableName, databaseInfo.getDatabaseDrivers());

        while (tableInfo.next()) {
            String[] lines = tableInfo.getString(2).split("\n");

            if (lines.length < 1) {
                throw new DataException("No Data on columns in table " + tableName);
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
        }

        return mappedTable;
    }
}
