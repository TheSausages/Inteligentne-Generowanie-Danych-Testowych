package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionInformation {
    String driver;

    String databaseUrl;

    String username;

    String password;

    public ConnectionInformation() {}

    public ConnectionInformation(String driver, String databaseUrl, String username, String password) {
        this.driver = driver;
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
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

            Connection connection = DriverManager.getConnection(this.databaseUrl, this.username, this.password);

            ResultSet resultSet = connection.createStatement().executeQuery("Select * from Courses");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + ":" + resultSet.getString(2));
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("There was a problem connecting to the database!");
            e.printStackTrace();
        }
    }
}
