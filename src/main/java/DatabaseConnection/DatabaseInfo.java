package DatabaseConnection;

import Exceptions.ConnectionException;
import TableMapping.ColumnMappingClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Class containing information on database
 */

@Setter
@Getter
@NoArgsConstructor
public class DatabaseInfo {
    private SupportedDatabases supportedDatabase;

    private String databaseUrl;

    private String databaseName;

    private String username;

    private String password;

    public DatabaseInfo(SupportedDatabases supportedDatabase) {
        this.supportedDatabase = supportedDatabase;
    }

    private void setAccountInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void createAndSaveURL(String hostnameOrServerName, String portOrInstance, String databaseName) {

        if (portOrInstance.isEmpty()) {
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

    public static ColumnMappingClass.ColumnBuilder builder() {
        return new ColumnMappingClass.ColumnBuilder();
    }

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
                throw new IllegalStateException("You must select a database!");
            }

            if (hostnameOrServerName.isEmpty() || portOrInstance.isEmpty() || databaseName.isEmpty()) {
                throw new IllegalStateException("Database information cannot be empty!");
            }

            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalStateException("Account Credentials cannot be empty!");
            }

            DatabaseInfo databaseInfo = new DatabaseInfo(this.supportedDatabase);
            databaseInfo.createAndSaveURL(this.hostnameOrServerName, this.portOrInstance, this.databaseName);
            databaseInfo.setAccountInfo(this.username, this.password);

            return databaseInfo;
        }
    }
}
