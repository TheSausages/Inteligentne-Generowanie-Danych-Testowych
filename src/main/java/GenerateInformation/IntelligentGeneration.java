package GenerateInformation;

import DataCreation.ColumnNameMapping;
import DataCreation.MakeDoubleTabelForSeedInterface;
import DataCreation.RandBetween;
import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
/*import Gui.MainGui; */
import Gui.MainGui;
import Gui.MainGuiController;
import InsertCreation.InsertCreationClass;
import InsertCreation.InsertSavingClass;
import TableMapping.TableMappingClass;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static javafx.application.Application.launch;

/**
 * Main class of the program. Has the launching methods for each database type and GUI launch.
 */
@NoArgsConstructor
public class IntelligentGeneration {
    /**
     * See {@link Settings}
     */
    private Settings settings;

    /**
     * See {@link ConnectionInformation}
     */
    private ConnectionInformation connectionInformation;

    /**
     * Launch Gui for the program
     * @param args the parameters for the Gui
     */
    public static void launchGui(String[] args) {
       launch(MainGui.class, args);
    }

    public void generateForGui(SupportedDatabases databases, String hostname, String port, String databaseName, String username, String password, long seed, String locale, String tableMappingFile, String insertFilePath, boolean autoFill) {
        this.settings = new Settings();

        this.settings.setDatabaseInfo(DatabaseInfo.builder()
                .database(databases)
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

        operationList(true);
    }

    /**
     * Method that reads the Settings.json file to its representation {@link Settings}
     * @param path path to the Settings.json file
     */
    public void useSettingsFromFile(String path) {
        if (StringUtils.isBlank(path)) {
            path = "Settings.json";
        }

        this.settings = JSONFileOperator.mapSettingsFile(path);

        operationList(false);
    }

    /**
     * Generate the {@link Settings} class for the Oracle database using information provided as parameters
     * @param hostname Hostname where the database runs
     * @param port Port on which the database runs
     * @param databaseName Name of the database
     * @param username Username of the account used to connect to the database
     * @param password Password of the account used to connect to the database
     * @param seed Seed used to generate data
     * @param locale {@link Locale} class used to generate data, should be written in string form like "pl-PL"
     * @param tableMappingFile path to a .json file where the information about the tables mapping will be located
     * @param insertFilePath path to a .json file where the INSERT statements will be located
     * @param autoFill if true, the INSERT statements will be executed directly to the database
     */
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

        operationList(false);
    }

    /**
     * Generate the {@link Settings} class for the MySQL database using information provided as parameters
     * @param hostname Hostname where the database runs
     * @param port Port on which the database runs
     * @param databaseName Name of the database
     * @param username Username of the account used to connect to the database
     * @param password Password of the account used to connect to the database
     * @param seed Seed used to generate data
     * @param locale {@link Locale} class used to generate data, should be written in string form like "pl-PL"
     * @param tableMappingFile path to a .json file where the information about the tables mapping will be located
     * @param insertFilePath path to a .json file where the INSERT statements will be located
     * @param autoFill if true, the INSERT statements will be executed directly to the database
     */
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

        operationList(false);
    }

    /**
     * Generate the {@link Settings} class for the Oracle database using information provided as parameters
     * @param server Server on which the database runs
     * @param instance Instance on which the database runs
     * @param databaseName Name of the database
     * @param username Username of the account used to connect to the database
     * @param password Password of the account used to connect to the database
     * @param seed Seed used to generate data
     * @param locale {@link Locale} class used to generate data, should be written in string form like "pl-PL"
     * @param tableMappingFile path to a .json file where the information about the tables mapping will be located
     * @param insertFilePath path to a .json file where the INSERT statements will be located
     * @param autoFill if true, the INSERT statements will be executed directly to the database
     */
    public void generateForSQLServerDatabase(String server, String instance, String databaseName, String username, String password, long seed, String locale, String tableMappingFile, String insertFilePath, boolean autoFill) {
        this.settings = new Settings();

        this.settings.setDatabaseInfo(DatabaseInfo.builder()
                .database(SupportedDatabases.SQLSERVER)
                .hostOrServerName(server)
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

        operationList(false);
    }

    /**
     * List of operations(methods) that will be executed in the given order to generate data
     * @param isGui bool value that checks is gui is used
     */
    private void operationList(boolean isGui) {
        try {
            this.connectionInformation = new ConnectionInformation(settings.getDatabaseInfo());
            connectionInformation.connect();

            writeStructureToFile(connectionInformation.getTableInfo(), settings.getMappingDataPath());
            connectionInformation.closeConnection();

            if (isGui) {
                MainGuiController.showAlertMessage(
                        "You can now see the Mapping data inside the file:" + settings.getMappingDataPath(),
                        "After confirming the data within the file press 'Generate Data'"
                );
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.println("You can now see the Mapping data inside the file:" + settings.getMappingDataPath());
                System.out.println("Type 'con' to continue");

                do {
                    String decision = scanner.next();

                    if (decision.equals("con")) break;
                }while(true);
            }


            List<TableMappingClass> tables = readStructureFromFile(settings.getMappingDataPath());
            if (settings.isAutoFill()) {
                insertsToDatabase(tables, generateData(tables));
            } else {
                insertsToFile(tables, generateData(tables));
            }

            if (isGui) {
                MainGuiController.showClosingMessage("The Data has been Generated!",
                        settings.isAutoFill() ? "The Data has been inserted directly into the database" : "The Data has been inserted into the " + settings.getInsertPath() + " file");
            } else {
                System.out.println(settings.isAutoFill() ? "The Data has been inserted directly into the database" : "The Data has been inserted into the " + settings.getInsertPath() + " file");
            }
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that, using {@link JSONFileOperator}, writes list of {@link TableMappingClass} into a .json file - its path can be given as an argument in {@link Settings} class
     * @param tables List of {@link TableMappingClass} representing the database table structure
     * @param path path to the .json file where the list will be saved
     */
    private void writeStructureToFile(List<TableMappingClass> tables, String path) {
        JSONFileOperator.tableJSONToFile(tables, path);
    }

    /**
     * Method that, using {@link JSONFileOperator}, reads the information inside the .json file located in {@param path} created using {@link IntelligentGeneration#writeStructureToFile(List, String)}
     *  and create their representation using {@link TableMappingClass}
     * @param path path to the .json file where the information about mapping is stored
     * @return List of {@link TableMappingClass} representing the database table structure
     */
    private List<TableMappingClass> readStructureFromFile(String path) {
        return JSONFileOperator.fileToTableJSON(path);
    }

    /**
     * Method that generated data for the list of {@link TableMappingClass} received as param
     * @param tables path to the .json file where the information about mapping is stored
     * @return List of 2-dim arrays that stored the generated data
     */
    private List<String[][]> generateData(List<TableMappingClass> tables) {
        final List<String[][]> tableData = new ArrayList<>();

        tables.forEach(table -> {
            List<String[]> data = new ArrayList<>();

            table.getColumns().forEach(column -> {

                //Foreign Keys, need to change
                if (column.getForeignKey().isForeignKey()) {
                    String[] foreignKeyData = new String[table.getNumberOfGenerations()];
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

                String[] generated = (ColumnNameMapping.getGenerator(column)).generate(this.settings.getSeed(), table.getNumberOfGenerations(), this.settings.getLocale(), column);

                for(int i = 0; i < generated.length; i++) {
                    generated[i] = generated[i].replaceAll("'+", "-");
                }

                data.add(generated);
            });

            tableData.add(data.toArray(new String[][]{}));

            settings.seedIncrement();
        });

        return tableData;
    }

    /**
     * Method that connects again to the database and executes the generated Insert Statements in the database
     * @param tables The structure of {@link TableMappingClass} mapped from the database
     * @param data The data generated in {@link IntelligentGeneration#generateData(List)}
     */
    private void insertsToDatabase(List<TableMappingClass> tables, List<String[][]> data) {
        connectionInformation.connect();
        connectionInformation.insertsToDatabase(generateInserts(tables, data));
        connectionInformation.closeConnection();
    }

    /**
     * Method that writes the generated Insert Statements to a .txt file, whose path can be set using {@link Settings}
     * @param tables The structure of {@link TableMappingClass} mapped from the database
     * @param data The data generated in {@link IntelligentGeneration#generateData(List)}
     */
    private void insertsToFile(List<TableMappingClass> tables, List<String[][]> data) {
        new InsertSavingClass(settings.getInsertPath()).saveToFile(generateInserts(tables, data));
    }

    /**
     * Method that generated Insert statements
     * @param tables The structure of {@link TableMappingClass} mapped from the database
     * @param data The data generated in {@link IntelligentGeneration#generateData(List)}
     * @return List of {@link String} that contain the Insert Statements containing the generated data
     */
    private List<String> generateInserts(List<TableMappingClass> tables, List<String[][]> data) {
        return new InsertCreationClass().insertCreationClass(tables, data);
    }
}
