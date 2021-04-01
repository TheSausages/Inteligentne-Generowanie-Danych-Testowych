package DatabaseConnection;

import Exceptions.ConnectionException;
import TableMapping.TableMapper;
import TableMapping.TableMappingClass;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing information about the connection to database
 */
public class ConnectionInformation {

    private HikariDataSource hikariDataSource;

    private Connection connection;

    private DatabaseInfo databaseInfo;

    public ConnectionInformation() {}

    /**
     * Creates A HikariDataSource from {@link DatabaseInfo}
     * @param databaseInfo Contains information on database like: user info, database url, databse type
     */
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

    public List<TableMappingClass> getTableInfo() {
        switch (databaseInfo.getDatabaseDrivers()) {
            case ORACLE -> getTableResultOracle();
            case SQLSERVER -> getTableResultSetSQLServer();
            case MYSQL -> {
                return getTableResultSetMySql();
            }
        }

        return null;
    }

    /**
     *
     * @return Returns a list of table models in form of {@link TableMappingClass}
     */
    //MySQL section
    public List<TableMappingClass> getTableResultSetMySql() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet resultSet = connection.getMetaData().getTables(databaseInfo.getDatabaseName(), null, "%", new String[]{"TABLE"});

            TableMapper tableMapper = new TableMapper(databaseInfo);
            List<ResultSet> acquiredInformation = new ArrayList<>();

            while (resultSet.next()) {
                acquiredInformation.add(connection.createStatement().executeQuery("SHOW CREATE TABLE " + resultSet.getString(3)));
            }

            return tableMapper.mapMySqlTable(acquiredInformation);
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    //OracleSection
    public void getTableResultOracle() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select object_name from sys.all_objects where object_type = 'TABLE' and owner != 'SYS' and created > (Select created from V$DATABASE)");

            while (resultSet.next()) {

                ResultSet resultSet1 = connection.createStatement().executeQuery("select dbms_metadata.get_ddl( 'TABLE', '" + resultSet.getString(1) +"' ) from dual");


                while (resultSet1.next()) {
                    System.out.println(resultSet1.getString(1)); //dużo informacji na temat tabeli
                }
            }

        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    //SQLServer Section
    public void getTableResultSetSQLServer() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT name FROM sys.objects WHERE type = 'U' and name Not In ('dtproperties','sysdiagrams');");


            while (resultSet.next()) {
                ResultSet resultSet1 = connection.createStatement().executeQuery("Select * from INFORMATION_SCHEMA.COLUMNS where Table_name = '" + resultSet.getString(1) + "'");

                while (resultSet1.next()) {
                    System.out.println(resultSet1.getString(1)); //nazwa katalogu
                    System.out.println(resultSet1.getString(2)); //nazwa schema
                    System.out.println(resultSet1.getString(3)); //nazwa tabeli
                    System.out.println(resultSet1.getString(4)); //nazwa columny
                    System.out.println(resultSet1.getString(5)); //ordinal position
                    System.out.println(resultSet1.getString(6)); //column Default
                    System.out.println(resultSet1.getString(7)); //is nullable
                    System.out.println(resultSet1.getString(8)); //data type
                    System.out.println(resultSet1.getString(9)); //max length
                    System.out.println(resultSet1.getString(10)); //precision
                    System.out.println(resultSet1.getString(11)); //precision radix
                    System.out.println(resultSet1.getString(12)); //numeric scale
                }
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
