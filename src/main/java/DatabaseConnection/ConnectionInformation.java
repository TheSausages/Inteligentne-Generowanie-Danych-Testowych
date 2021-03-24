package DatabaseConnection;

import Exceptions.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionInformation {
    DatabaseDrivers databaseDrivers;

    String databaseUrl;

    String username;

    String password;

    Connection connection;

    public ConnectionInformation() {}

    public ConnectionInformation(DatabaseDrivers databaseDrivers) {
        this.databaseDrivers = databaseDrivers;
    }

    public ConnectionInformation(DatabaseDrivers databaseDrivers, String username, String password) {
        this.databaseDrivers = databaseDrivers;
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setDatabaseDrivers(DatabaseDrivers databaseDrivers) {
        this.databaseDrivers = databaseDrivers;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseDrivers getDatabaseDrivers() {
        return databaseDrivers;
    }

    public void setAccountInfo(String username, String password) {
        if (username == null || password == null) {
            throw new ConnectionException("Account data cannot be left blank!");
        } else {
            this.username = username;
            this.password = password;
        }
    }

    public void createAndSaveURL(String hostnameOrServerName, String portOrInstance, String databaseName) {
        switch (this.databaseDrivers) {
            case MYSQL -> this.databaseUrl = "jdbc:mysql://" + hostnameOrServerName + ":" + portOrInstance + "/" + databaseName;

            case ORACLE -> this.databaseUrl = "jdbc:oracle:thin:@" + hostnameOrServerName + ":" + portOrInstance + ":" + databaseName;

            case SQLSERVER -> this.databaseUrl = "jdbc:sqlserver://" + hostnameOrServerName + "\\" + portOrInstance + ";databaseName=" + databaseName;
        }
    }

    public void connect() {
        try {
            Class.forName(this.databaseDrivers.driverString);

            this.connection = DriverManager.getConnection(this.databaseUrl, this.username, this.password);

            System.out.println(connection.isValid(1));
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionException("There was a problem connecting to the " + this.databaseDrivers + " database!");
        }
    }

    public void getTableInfo() {
        try {
            ResultSet resultSet = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            while(resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
            }

        } catch (SQLException e) {
            throw new ConnectionException("Could not get table metadata!");
        }
    }

    public void closeConnection()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionException("Could not close connection!");
        }

    }
}
