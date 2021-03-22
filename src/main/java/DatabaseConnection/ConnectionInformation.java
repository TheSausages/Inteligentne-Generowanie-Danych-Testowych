package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionInformation {

    String databaseUrl;

    String username;

    String password;

    Connection connection;

    public ConnectionInformation() {}

    public ConnectionInformation(String databaseUrl, String username, String password) {
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

    public void connectOracle() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            Connection connection = DriverManager.getConnection(this.databaseUrl, this.username, this.password);

            System.out.println("Is Oracle connection Valid:" + connection.isValid(1));

            this.connection = connection;
        } catch (SQLException e) {
            System.out.println("There was a problem connecting to the Oracle database!");
            e.printStackTrace();
        }
    }

    public void connectMySql() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            Connection connection = DriverManager.getConnection(this.databaseUrl, this.username, this.password);

            System.out.println("Is MySql connection Valid:" + connection.isValid(1));

            this.connection = connection;
        } catch (SQLException e) {
            System.out.println("There was a problem connecting to the MySql database!");
            e.printStackTrace();
        }
    }

    public void connectSqlServer() {
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

            Connection connection = DriverManager.getConnection(this.databaseUrl, this.username, this.password);

            System.out.println("Is MSSql connection Valid:" + connection.isValid(1));

            this.connection = connection;
        } catch (SQLException e) {
            System.out.println("There was a problem connecting to the MSSql database!");
            e.printStackTrace();
        }
    }

    public void getTableInfo() {
        try {
            System.out.println("weszlo");

            ResultSet resultSet = connection.getMetaData().getTables(null, null, null, new String[]{"Table"});

            System.out.println("weszlo");

            while(resultSet.next())
            {
                System.out.println(resultSet.getString("TABLE_NAME"));
            }

            System.out.println("weszlo");
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
