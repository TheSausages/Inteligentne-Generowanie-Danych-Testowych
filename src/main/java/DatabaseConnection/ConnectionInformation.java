package DatabaseConnection;

import Exceptions.ConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionInformation {

    private HikariDataSource hikariDataSource;

    private Connection connection;

    public ConnectionInformation() {}

    public void createDataSource(DatabaseInfo databaseInfo) {
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
        try {
            System.out.println("WESZLO1");
            ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", null);
            System.out.println("WESZLO2");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("TABLE_NAME"));
            }
            System.out.println("WESZLO3");
        } catch (SQLException e) {
            throw new ConnectionException("Could not get table metadata!");
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
