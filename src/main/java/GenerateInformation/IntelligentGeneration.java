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
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static javafx.application.Application.launch;

@NoArgsConstructor
public class IntelligentGeneration {
    private Settings settings;

    @Deprecated
    public void launchGui(String[] args) {
        launch(MainGui.class, args);
    }

    public void getSettingsFromFile(String path) {
        if (StringUtils.isBlank(path)) {
            path = "Settings.txt";
        }

        this.settings = JSONFileOperator.mapSettingsFile(path);

        operationList();
    }

    public void generateForOracleDatabase(String hostname, String port, String databaseName, String username, String password, long seed) {
        this.settings = new Settings();

        this.settings.setDatabaseInfo(DatabaseInfo.builder()
                .database(SupportedDatabases.ORACLE)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build());
        this.settings.setSeed(seed);

        operationList();
    }

    public void generateForMySQLDatabase(String hostname, String port, String databaseName, String username, String password, long seed) {
        this.settings = new Settings();

        this.settings.setDatabaseInfo(DatabaseInfo.builder()
                .database(SupportedDatabases.MYSQL)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build());
        this.settings.setSeed(seed);

        operationList();
    }

    public void generateForSQLServerDatabase(String hostname, String instance, String databaseName, String username, String password, long seed) {
        this.settings = new Settings();

        this.settings.setDatabaseInfo(DatabaseInfo.builder()
                .database(SupportedDatabases.SQLSERVER)
                .hostOrServerName(hostname)
                .portOrInstance(instance)
                .name(databaseName)
                .username(username)
                .password(password)
                .build());
        this.settings.setSeed(seed);

        operationList();
    }

    private void operationList() {
        try {
            ConnectionInformation connectionInformation = new ConnectionInformation(settings.getDatabaseInfo());
            connectionInformation.connect();

            writeStructureToFile(connectionInformation.getTableInfo(), settings.getMappingDataPath());
            connectionInformation.closeConnection();

            System.out.println("You can now see the Mapping data inside the file:" + settings.getMappingDataPath());
            Scanner scanner = new Scanner(System.in);
            scanner.next();

            generateData(readStructureFromFile(settings.getMappingDataPath()));
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeStructureToFile(List<TableMappingClass> tables, String path) {
        JSONFileOperator.tableJSONToFile(tables, path);
    }

    private List<TableMappingClass> readStructureFromFile(String path) {
        return JSONFileOperator.fileToTableJSON(path);
    }

    private void generateData(List<TableMappingClass> tables) {
        final List<String[][]> tableData = new ArrayList<>();

        tables.forEach(table -> {
            List<String[]> data = new ArrayList<>();

            table.getColumns().forEach(column -> {

                if (column.isAutoIncrement()) {
                    return;
                }

                data.add((ColumnNameMapping.getGenerator(column)).generate(this.settings.getSeed(), table.getNumberOfGenerations(), this.settings.getLocale()));
            });

            tableData.add(data.toArray(new String[][]{}));

            settings.seedIncrement();
        });

        generateFile(tables, tableData);
    }

    private void generateFile(List<TableMappingClass> tables, List<String[][]> data) {
        String str = new InsertCreationClass().insertCreationClass(tables, data);
        new InsertSavingClass(settings.getInsertPath()).saveToFile(str);
    }
}
