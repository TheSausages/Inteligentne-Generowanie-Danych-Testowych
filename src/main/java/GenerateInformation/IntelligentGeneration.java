package GenerateInformation;

import DataCreation.ColumnNameMapping;
import DataCreation.MakeDoubleTabelForSeedInterface;
import DataCreation.RandBetween;
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

    public void launchGui(String[] args) {
        launch(MainGui.class, args);
    }

    public void getSettingsFromFile(String path) {
        if (StringUtils.isBlank(path)) {
            path = "Settings.json";
        }

        this.settings = JSONFileOperator.mapSettingsFile(path);

        operationList();
    }

    public void generateForOracleDatabase(String hostname, String port, String databaseName, String username, String password, long seed, String locale, String tableMappingFile, String insertFilePath, boolean autoFill) {
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
        this.settings.setInsertPath(insertFilePath);
        this.settings.setMappingDataPath(tableMappingFile);
        this.settings.setLocale(locale);
        this.settings.setAutoFill(autoFill);

        operationList();
    }

    public void generateForMySQLDatabase(String hostname, String port, String databaseName, String username, String password, long seed, String locale, String tableMappingFile, String insertFilePath, boolean autoFill) {
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
        this.settings.setInsertPath(insertFilePath);
        this.settings.setMappingDataPath(tableMappingFile);
        this.settings.setLocale(locale);
        this.settings.setAutoFill(autoFill);

        operationList();
    }

    public void generateForSQLServerDatabase(String hostname, String instance, String databaseName, String username, String password, long seed, String locale, String tableMappingFile, String insertFilePath, boolean autoFill) {
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
        this.settings.setInsertPath(insertFilePath);
        this.settings.setMappingDataPath(tableMappingFile);
        this.settings.setLocale(locale);
        this.settings.setAutoFill(autoFill);

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

                if (column.getForeignKey().isForeignKey()) {
                    String[] foreignKeyData = new String[tables.stream().filter(tableForeign -> tableForeign .getTableName().equals(column.getForeignKey().getForeignKeyTable())).findFirst().get().getNumberOfGenerations()];
                    double[] doubles = MakeDoubleTabelForSeedInterface.generateDoubleArray(this.settings.getSeed(), foreignKeyData.length);

                    for (int i = 0; i < foreignKeyData.length; i++) {
                        foreignKeyData[i] = Integer.toString(RandBetween.randint(0, foreignKeyData.length, doubles[i]));
                    }

                    data.add(foreignKeyData);
                    return;
                }

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
