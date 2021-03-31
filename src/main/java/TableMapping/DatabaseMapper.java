package TableMapping;

import DatabaseConnection.DatabaseInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMapper {
    DatabaseInfo databaseInfo;

    public DatabaseMapper(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public TableMappingClass mapMySqlTable(ResultSet tableInfo, String tablename) throws SQLException {
        TableMappingClass mappedTable = new TableMappingClass(tablename, databaseInfo.getDatabaseDrivers());

        while (tableInfo.next()) {
            String[] lines = tableInfo.getString(2).split("\n");

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

                    String columnName = names[3];
                    String referencedTable = names[5];
                    String referencedColumn = names[7];

                    mappedTable.getColumns().stream()
                            .filter(columnMappingClass -> columnName.equals(columnMappingClass.getName()))
                            .findFirst().get().getForeignKey().foreignKeyInfo(referencedColumn, referencedTable);
                    continue;
                }

                if (lines[lineIndex].contains("KEY `")) {
                    continue;
                }

                ColumnMappingClass column = new ColumnMappingClass();

                lines[lineIndex] = lines[lineIndex]
                        .replace(" unsigned", "-unsigned")
                        .replace("NOT NULL", "NOT-NULL")
                        .replace("TINYINT(1)", "BOOLEAN")
                        .replace("DEFAULT ", "DEFAULT-");

                String[] words = lines[lineIndex].split(" ");

                column.mapColumnsMySQL(words);

                mappedTable.addColumn(column);
            }
        }

        return mappedTable;
    }
}