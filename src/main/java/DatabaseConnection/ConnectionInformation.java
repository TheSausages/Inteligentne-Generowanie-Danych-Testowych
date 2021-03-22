package DatabaseConnection;

import javax.xml.crypto.Data;
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

    public ConnectionInformation(DatabaseDrivers databaseDrivers, String databaseUrl, String username, String password) {
        this.databaseDrivers = databaseDrivers;
        this.databaseUrl = databaseUrl;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void connect() {
        try {
            Class.forName(this.databaseDrivers.driverString);

            Connection connection = DriverManager.getConnection(this.databaseUrl, this.username, this.password);

            System.out.println("Is " + databaseDrivers + " connection Valid:" + connection.isValid(1));

            this.connection = connection;
        } catch (SQLException | NullPointerException | ClassNotFoundException e) {
            System.out.println("There was a problem connecting to the " + this.databaseDrivers + " database!");
            e.printStackTrace();
        }
    }

    public void getTableInfo() {
        try {
            ResultSet resultSet = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            while(resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String remarks = resultSet.getString("REMARKS");
            }

        } catch (SQLException e) {
            System.out.println("Could not get table metadata!");
        }
    }

    public void closeConnection()  {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Could not close connection!");
            e.printStackTrace();
        }

    }
}
