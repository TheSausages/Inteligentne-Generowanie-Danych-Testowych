package DatabaseConnection;

import Exceptions.ConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionInformation {

    private HikariDataSource hikariDataSource;

    private Connection connection;

    private DatabaseInfo databaseInfo;

    public ConnectionInformation() {}

    public void createDataSource(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseInfo.getDatabaseUrl());
        hikariConfig.setUsername(databaseInfo.getUsername());
        hikariConfig.setPassword(databaseInfo.getPassword());
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        try {
            hikariDataSource = new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            throw new ConnectionException("Please check if the inserted Data is correct!");
        }
    }

    public void connect() {
        try {
            connection = hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("There was a problem connecting to the database!");
        }
    }

    public void closeConnection()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionException("Could not close connection!");
        }
    }

    public void getTableInfo() {
        getTableResultSetMySql();
        /*try {
            getTableResultSetMySql();
            //String[] types = { "TABLE" };
            //ResultSet resultSet = connection.getMetaData()
            //        .getTables(null, null, "%", types);
            /*ResultSet[] resultSet = getTableResultSetMySql();

            System.out.println("weszlo");

            for (int i = 0; i < resultSet.length; i++) {
                System.out.println(resultSet[i].getString(1));
            }
        } catch (SQLException e) {
            throw new ConnectionException("Could not get table metadata!");
        }*/
    }

    public void getTableResultSetMySql() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet resultSet = connection.getMetaData().getTables(databaseInfo.getDatabaseName(), null, "%", new String[]{"TABLE"});

            System.out.println("1");
            //List<ResultSet> columns = new ArrayList<>();

            int  i = 0;
            while (resultSet.next()) {
                System.out.println("Nowa tabela");
                System.out.println(resultSet.getString(3));
                //Query
                ResultSet resultSet1 = connection.createStatement().executeQuery("SHOW COLUMNS FROM " + resultSet.getString(3));

                while (resultSet1.next()) {
                    ResultSetMetaData rsmd = resultSet1.getMetaData();

                    //Get number of columns returned
                    int numOfCols = rsmd.getColumnCount();

                    //Print out type for each column
                    /*for (int a = 1; a <= numOfCols; ++a) {

                        System.out.println(resultSet1.getString(a));
                    }*/

                    System.out.println(resultSet1.getString(1) + ":" + resultSet1.getString(2) + ", is key:" + resultSet1.getString(4) + ", is auto-increment:" +  resultSet1.getString(6));

                    System.out.println();
                }

                    /*ResultSetMetaData rsmd = resultSet1.getMetaData();

                    //Get number of columns returned
                    int numOfCols = rsmd.getColumnCount();

                    //Print out type for each column
                    for (int a = 1; a <= numOfCols; ++a) {
                        System.out.println("Column [" + a + "] data type: " + rsmd.getColumnName(a) + ":" + rsmd.getColumnTypeName(a));
                    }*/
                System.out.println("");
                i++;
            }

        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }



    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setHikariDataSource(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
