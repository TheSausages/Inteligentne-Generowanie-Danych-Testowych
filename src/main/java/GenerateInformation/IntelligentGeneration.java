package GenerateInformation;

import DataCreation.ColumnNameMapping;
import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import Exceptions.ConnectionException;
import Gui.MainGui;
import InsertCreation.InsertCreationClass;
import InsertCreation.InsertSavingClass;
import TableMapping.ColumnMappingClass;
import TableMapping.TableMappingClass;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

import static javafx.application.Application.launch;

@NoArgsConstructor
public class IntelligentGeneration {
    private final int numberOfGeneratedData = 1;
    private long seed;

    public void launchGui(String[] args) {
        launch(MainGui.class, args);
    }

    public void getSettingsFromFile(String path) {
        if (StringUtils.isBlank(path)) {
            path = "Settings.txt";
        }

        String[] data = new String[7];

        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            int i = 0;
            for (String line = fileReader.readLine(); line != null; line = fileReader.readLine()) {
                String[] lineData = line.split(":");

                if (lineData.length < 2 || StringUtils.isEmpty(lineData[1])) {
                    data[i] = "";
                } else {
                    data[i] = lineData[1].trim();
                }

                i++;
            }

            switch (data[0]) {
                case "MySQL" -> generateForMySQLDatabase(data[1], data[2], data[3], data[4], data[5], Long.parseLong(data[6]));
                case "Oracle" -> generateForOracleDatabase(data[1], data[2], data[3], data[4], data[5], Long.parseLong(data[6]));
                case "SQLServer" -> generateForSQLServerDatabase(data[1], data[2], data[3], data[4], data[5], Long.parseLong(data[6]));
                default -> System.out.println("This Database is not Supported!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Seed number should consist of only numbers!");
        }
    }

    public void generateForOracleDatabase(String hostname, String port, String databaseName, String username, String password, long seed) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.ORACLE)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        this.seed = seed;

        connectToDatabase(databaseInfo);
    }

    public void generateForMySQLDatabase(String hostname, String port, String databaseName, String username, String password, long seed) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.MYSQL)
                .hostOrServerName(hostname)
                .portOrInstance(port)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        this.seed = seed;

        connectToDatabase(databaseInfo);
    }

    public void generateForSQLServerDatabase(String hostname, String instance, String databaseName, String username, String password, long seed) {
        DatabaseInfo databaseInfo = DatabaseInfo.builder()
                .database(SupportedDatabases.SQLSERVER)
                .hostOrServerName(hostname)
                .portOrInstance(instance)
                .name(databaseName)
                .username(username)
                .password(password)
                .build();

        this.seed = seed;

        connectToDatabase(databaseInfo);
    }

    private void connectToDatabase(DatabaseInfo databaseInfo) {
        try {
            ConnectionInformation connectionInformation = new ConnectionInformation(databaseInfo);
            connectionInformation.connect();

            //generateData(connectionInformation.getTableInfo());
            writeStructureToFile(connectionInformation.getTableInfo());

            connectionInformation.closeConnection();
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeStructureToFile(List<TableMappingClass> tables) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("TableMapping.txt"))) {
            for (TableMappingClass table : tables) {
                writer.write("Table Name:" + table.getTableName() + " from " + table.getTableType() + " database");
                writer.newLine();

                for (ColumnMappingClass column : table.getColumns()) {
                    writer.newLine();

                    for (String info : column.getColumnStructureIntoList()) {
                        writer.write(info);
                        writer.newLine();
                    }
                }

                writer.newLine();
            }
        } catch (IOException e) {
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
        String str = new InsertCreationClass().InsertCreationClass(tables, data);
        new InsertSavingClass().InsertSavingClass(str);
    }
}
