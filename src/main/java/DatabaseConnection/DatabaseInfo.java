package DatabaseConnection;

import Exceptions.ConnectionException;
import lombok.Getter;
import lombok.Setter;

/**
 * Class containing information on database
 */
@Setter
@Getter
public class DatabaseInfo {
    private SupportedDatabases supportedDatabases;

    private String databaseUrl;

    private String databaseName;

    private String username;

    private String password;

    public DatabaseInfo() {}

    public DatabaseInfo(SupportedDatabases supportedDatabases) {
        this.supportedDatabases = supportedDatabases;
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
            switch (supportedDatabases) {
                case ORACLE -> portOrInstance = "1521";
                case MYSQL -> portOrInstance = "3306";
            }
        }


        switch (this.supportedDatabases) {
            case MYSQL -> this.databaseUrl = "jdbc:mysql://" + hostnameOrServerName + ":" + portOrInstance;

            case ORACLE -> this.databaseUrl = "jdbc:oracle:thin:@" + hostnameOrServerName + ":" + portOrInstance  + ":" + databaseName;

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

    public void setDatabaseDrivers(SupportedDatabases supportedDatabases) {
        this.supportedDatabases = supportedDatabases;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
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

    public SupportedDatabases getDatabaseDrivers() {
        return supportedDatabases;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
