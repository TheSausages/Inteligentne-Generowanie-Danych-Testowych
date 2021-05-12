package DatabaseConnection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;


/**
 * Class containing information about the database
 */
@Setter
@Getter
@NoArgsConstructor
public class DatabaseInfo {
    /**
     * information about what type the database type is. List found in {@link SupportedDatabases}
     */
    private SupportedDatabases supportedDatabase;

    /**
     * Url used to connect to the database
     */
    private String databaseUrl;

    /**
     * The selected database name
     */
    private String databaseName;

    /**
     * Username of the account used to connect to the database
     */
    private String username;

    /**
     * Password of the account used to connect to the database
     */
    private String password;

    /**
     * Creator for the Class
     * @param supportedDatabase Type of the database. List found in {@link SupportedDatabases}
     */
    public DatabaseInfo(SupportedDatabases supportedDatabase) {
        this.supportedDatabase = supportedDatabase;
    }

    /**
     * Method used to save account information used to connect to the database
     * @param username Username for the account
     * @param password Password for the account
     */
    private void setAccountInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Method used to create the {@link DatabaseInfo#databaseUrl} used to connect to the database.
     * @param hostnameOrServerName Hostname or Server Name that the database is located on
     * @param portOrInstance Port or Instance the Database is using
     * @param databaseName the Database name
     */
    private void createAndSaveURL(String hostnameOrServerName, String portOrInstance, String databaseName) {
        if (StringUtils.isEmpty(hostnameOrServerName)) {
            hostnameOrServerName = "localhost";
        }

        if (StringUtils.isEmpty(portOrInstance)) {
            switch (supportedDatabase) {
                case ORACLE -> portOrInstance = "1521";
                case MYSQL -> portOrInstance = "3306";
                case SQLSERVER -> portOrInstance = "sqlexpress";
            }
        }

        switch (this.supportedDatabase) {
            case MYSQL -> this.databaseUrl = "jdbc:mysql://" + hostnameOrServerName + ":" + portOrInstance;

            case ORACLE -> this.databaseUrl = "jdbc:oracle:thin:@" + hostnameOrServerName + ":" + portOrInstance  + ":" + databaseName;

            case SQLSERVER -> this.databaseUrl = "jdbc:sqlserver://" + hostnameOrServerName + "\\" + portOrInstance + ";databaseName=" + databaseName;
        }
    }

    /**
     * Returns the builder instance for the Class
     * @return Builder for the Class
     */
    public static DatabaseInfoBuilder builder() {
        return new DatabaseInfoBuilder();
    }

    /**
     * The builder pattern class for this Class. Information needed to create the Class are: a viable {@link SupportedDatabases}, the Database Name and Account Information
     */
    public static final class DatabaseInfoBuilder {
        private SupportedDatabases supportedDatabase;
        private String hostnameOrServerName;
        private String portOrInstance;
        private String databaseName;
        private String username;
        private String password;

        public DatabaseInfoBuilder database(SupportedDatabases databases) {
            this.supportedDatabase = databases;
            return this;
        }

        public DatabaseInfoBuilder hostOrServerName(String hostnameOrServerName) {
            this.hostnameOrServerName = hostnameOrServerName;
            return this;
        }

        public DatabaseInfoBuilder portOrInstance(String portOrInstance) {
            this.portOrInstance = portOrInstance;
            return this;
        }

        public DatabaseInfoBuilder name(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public DatabaseInfoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public DatabaseInfoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public DatabaseInfo build() {
            if (supportedDatabase == null) {
                throw new IllegalStateException("You must select a valid database!");
            }

            if (StringUtils.isBlank(databaseName)) {
                throw new IllegalStateException("Database name cannot be empty!");
            }

            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                throw new IllegalStateException("Account Credentials cannot be empty!");
            }

            DatabaseInfo databaseInfo = new DatabaseInfo(this.supportedDatabase);
            databaseInfo.setDatabaseName(this.databaseName);
            databaseInfo.createAndSaveURL(this.hostnameOrServerName, this.portOrInstance, this.databaseName);
            databaseInfo.setAccountInfo(this.username, this.password);

            return databaseInfo;
        }
    }
}
