package GenerateInformation;

import DataCreation.ColumnNameMapping;
import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
import Gui.MainGui;
import InsertCreation.InsertCreationClass;
import InsertCreation.InsertSavingClass;
import TableMapping.TableMappingClass;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

@NoArgsConstructor
public class IntelligentGeneration {
    private int numberOfGeneratedData;
    private long seed;

    public void launchGui(String[] args) {
        launch(MainGui.class, args);
    }

    public void generateForOracleDatabase(String hostname, String port, String databaseName, String username, String password, int numberOfGeneratedData, long seed) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.ORACLE)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        this.numberOfGeneratedData = numberOfGeneratedData;
        this.seed = seed;

        connectToDatabase(databaseInfo);
    }

    public void generateForMySQLDatabase(String hostname, String port, String databaseName, String username, String password, int numberOfGeneratedData, long seed) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.MYSQL)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        this.numberOfGeneratedData = numberOfGeneratedData;
        this.seed = seed;

        connectToDatabase(databaseInfo);
    }

    public void generateForSQLServerDatabase(String hostname, String instance, String databaseName, String username, String password, int numberOfGeneratedData, long seed) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.SQLSERVER)
                .hostOrServerName(hostname)
                .portOrInstance(instance)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        this.numberOfGeneratedData = numberOfGeneratedData;
        this.seed = seed;

        connectToDatabase(databaseInfo);
    }

    private void connectToDatabase(DatabaseInfo databaseInfo) {
        try {
            ConnectionInformation connectionInformation = new ConnectionInformation(databaseInfo);
            connectionInformation.connect();

            generateData(connectionInformation.getTableInfo());

            connectionInformation.closeConnection();
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void generateData(List<TableMappingClass> tables) {
        tables.forEach(table -> {

            var nachwile = new Object() {
                List<String[]> data = new ArrayList<>();
            };
            table.getColumns().forEach(column -> {

                if (column.isAutoIncrement()) {
                    return;
                }

                nachwile.data.add((ColumnNameMapping.getGenerator(column)).generate(seed, numberOfGeneratedData));
            });

            generateFile(tables, nachwile.data.toArray(new String[][]{}));
            nachwile.data = new ArrayList<>();
        });
    }

    private void generateFile(List<TableMappingClass> tables, String[][] data) {
        String str = new InsertCreationClass().insertCreationClass(tables, data);
        new InsertSavingClass().saveToFile(str);
    }
}
