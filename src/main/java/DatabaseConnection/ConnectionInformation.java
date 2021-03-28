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
        switch (databaseInfo.getDatabaseDrivers()) {
            case ORACLE -> getTableResultOracle();
            case SQLSERVER -> getTableResultSetSQLServer();
            case MYSQL -> getTableResultSetMySql();
        }

    }

    public void getTableResultSetMySql() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet resultSet = connection.getMetaData().getTables(databaseInfo.getDatabaseName(), null, "%", new String[]{"TABLE"});

            System.out.println("1");

            int  i = 0;
            while (resultSet.next()) {
                System.out.println("Nowa tabela");

                ResultSet resultSet1 = connection.createStatement().executeQuery("SHOW CREATE TABLE " + resultSet.getString(3));

                while (resultSet1.next()) {
                    System.out.println(resultSet1.getString(1));//nazwa tabeli
                    System.out.println(resultSet1.getString(2)); //info na jkej temat (nawet do czego się odwołuje)
                }

                System.out.println("");
            }

        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    public void getTableResultOracle() {
        try {

            ResultSet resultSet = connection.createStatement().executeQuery("select object_name from sys.all_objects where object_type = 'TABLE' and owner != 'SYS' and created > (Select created from V$DATABASE)");

            while (resultSet.next()) {
                System.out.println("TABLE:" + resultSet.getString(1));
                //Query
                ResultSet resultSet1 = connection.createStatement().executeQuery("select dbms_metadata.get_ddl( 'TABLE', '" + resultSet.getString(1) +"' ) from dual");


                while (resultSet1.next()) {
                    System.out.println(resultSet1.getString(1));
                    System.out.println();
                }

                System.out.println("");
            }

        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    public void getTableResultSetSQLServer() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet resultSet = connection.getMetaData().getTables(databaseInfo.getDatabaseName(), null, "%", new String[]{"TABLE"});

            System.out.println("1");

            while (resultSet.next()) {
                System.out.println("Nowa tabela");
                System.out.println(resultSet.getString(3));
                //Query
                ResultSet resultSet1 = connection.createStatement().executeQuery("SHOW CREATE TABLE " + resultSet.getString(3));

                while (resultSet1.next()) {
                    System.out.println(resultSet1.getString(1));
                    System.out.println(resultSet1.getString(2));
                    System.out.println(resultSet1.getString(3));
                    System.out.println(resultSet1.getString(4));
                    System.out.println(resultSet1.getString(5));
                    System.out.println(resultSet1.getString(6));
                }

                    /*ResultSetMetaData rsmd = resultSet1.getMetaData();

                    //Get number of columns returned
                    int numOfCols = rsmd.getColumnCount();

                    //Print out type for each column
                    for (int a = 1; a <= numOfCols; ++a) {
                        System.out.println("Column [" + a + "] data type: " + rsmd.getColumnName(a) + ":" + rsmd.getColumnTypeName(a));
                    }*/
                System.out.println("");
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
