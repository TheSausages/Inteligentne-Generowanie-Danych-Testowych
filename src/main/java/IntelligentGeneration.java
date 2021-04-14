import DataCreation.ColumnNameMapping;
import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
import Gui.MainGui;
import TableMapping.TableMappingClass;

import java.util.List;

import static javafx.application.Application.launch;

public class IntelligentGeneration {
    public static void launchGui(String[] args) {
        launch(MainGui.class, args);
    }

    public static void generateForOracleDatabase(String hostname, String port, String databaseName, String username, String password) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.ORACLE)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        connectToDatabase(databaseInfo);
    }

    public static void generateForMySQLDatabase(String hostname, String port, String databaseName, String username, String password) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.MYSQL)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        connectToDatabase(databaseInfo);
    }

    public static void generateForSQLServerDatabase(String hostname, String instance, String databaseName, String username, String password) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.SQLSERVER)
                .hostOrServerName(hostname)
                .portOrInstance(instance)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        connectToDatabase(databaseInfo);
    }

    private static void connectToDatabase(DatabaseInfo databaseInfo) {
        try {
            ConnectionInformation connectionInformation = new ConnectionInformation(databaseInfo);
            connectionInformation.connect();

            generateData(connectionInformation.getTableInfo());

            connectionInformation.closeConnection();
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateData(List<TableMappingClass> tables) {
        tables.forEach(table -> table.getColumns().forEach(column -> {
            System.out.println(column.getName());

            if (column.isAutoIncrement()) {
                System.out.println(column.getName() + "is autoIncrement, so no mapping");
                System.out.println();
                return;
            }

            System.out.println(ColumnNameMapping.getGenerator(column));

            System.out.println();
        }));
    }
}
