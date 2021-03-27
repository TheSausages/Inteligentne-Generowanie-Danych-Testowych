package DatabaseConnection;

import Exceptions.ConnectionException;

public class DatabaseInfo {
    private DatabaseDrivers databaseDrivers;

    private String databaseUrl;

    private String username;

    private String password;

    public DatabaseInfo() {}

    public DatabaseInfo(DatabaseDrivers databaseDrivers) {
        this.databaseDrivers = databaseDrivers;
    }

    public void setAccountInfo(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            throw new ConnectionException("Account data cannot be left blank!");
        } else {
            this.username = username;
            this.password = password;
        }
    }

    public void createAndSaveURL(String hostnameOrServerName, String portOrInstance, String databaseName) {

        if (portOrInstance.isEmpty()) {
            switch (databaseDrivers) {
                case ORACLE -> portOrInstance = "1521";
                case MYSQL -> portOrInstance = "3306";
            }
        }


        switch (this.databaseDrivers) {
            case MYSQL -> this.databaseUrl = "jdbc:mysql://" + hostnameOrServerName + ":" + portOrInstance + "/" + databaseName;

            case ORACLE -> this.databaseUrl = "jdbc:oracle:thin:@" + hostnameOrServerName + ":" + portOrInstance + ":" + databaseName;

            case SQLSERVER -> this.databaseUrl = "jdbc:sqlserver://" + hostnameOrServerName + "\\" + portOrInstance + ";databaseName=" + databaseName;
        }
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

    public DatabaseDrivers getDatabaseDrivers() {
        return databaseDrivers;
    }
}
