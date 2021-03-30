package DatabaseConnection;

import Exceptions.ConnectionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseInfoTest {

    @Nested
    @DisplayName("Username and Password Tests")
    class usernameAndPasswordTest {
        @Test
        void setAccountInfo_AllData_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo();
            String username = "username";
            String password = "password";

            //when
            databaseInfo.setAccountInfo(username, password);

            //then
            assertEquals(username, databaseInfo.getUsername());
            assertEquals(password, databaseInfo.getPassword());
        }

        @Test
        void setAccountInfo_NoUsername_ThrowException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo();
            String username = "";
            String password = "password";

            //when
            Exception exception = assertThrows(ConnectionException.class, () -> databaseInfo.setAccountInfo(username, password));

            //then
            assertEquals(exception.getMessage(), "Account data cannot be left blank!");
        }

        @Test
        void setAccountInfo_NoPassword_ThrowException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo();
            String username = "username";
            String password = "";

            //when
            Exception exception = assertThrows(ConnectionException.class, () -> databaseInfo.setAccountInfo(username, password));

            //then
            assertEquals(exception.getMessage(), "Account data cannot be left blank!");
        }
    }

    @Nested
    @DisplayName("Create URL Test - MySql")
    class CreateURLMySQLTest {
        @Test
        void createAndSaveURL_AllData_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.MYSQL);
            String hostname = "hostname";
            String port = "3306";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://hostname:3306/database");
        }

        @Test
        void createAndSaveURL_NoPort_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.MYSQL);
            String hostname = "hostname";
            String port = "3306";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://hostname:3306/database");
        }

        @Test
        void createAndSaveURL_NoHostname_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.MYSQL);
            String hostname = "";
            String port = "3306";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://:3306/database");
        }

        @Test
        void createAndSaveURL_NoDatabaseName_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.MYSQL);
            String hostname = "hostname";
            String port = "3306";
            String databaseName = "";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://hostname:3306/");
        }
    }

    @Nested
    @DisplayName("Create URL Test - Oracle")
    class CreateURLOracleTest {
        @Test
        void createAndSaveURL_AllData_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.ORACLE);
            String hostname = "hostname";
            String port = "1521";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@hostname:1521:database");
        }

        @Test
        void createAndSaveURL_NoPort_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.ORACLE);
            String hostname = "hostname";
            String port = "";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@hostname:1521:database");
        }

        @Test
        void createAndSaveURL_NoHostname_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.ORACLE);
            String hostname = "";
            String port = "1521";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@:1521:database");
        }

        @Test
        void createAndSaveURL_NoDatabaseName_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.ORACLE);
            String hostname = "hostname";
            String port = "1521";
            String databaseName = "";

            //when
            databaseInfo.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@hostname:1521:");
        }
    }

    @Nested
    @DisplayName("Create URL Test - SQL Server")
    class CreateURLSQLServerTest {
        @Test
        void createAndSaveURL_AllData_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.SQLSERVER);
            String hostname = "hostname";
            String instance = "MSSQLSERVER";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, instance, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://hostname\\MSSQLSERVER;databaseName=database");
        }

        @Test
        void createAndSaveURL_NoInstance_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.SQLSERVER);
            String hostname = "hostname";
            String instance = "";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, instance, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://hostname\\;databaseName=database");
        }

        @Test
        void createAndSaveURL_NoHostname_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.SQLSERVER);
            String hostname = "";
            String instance = "MSSQLSERVER";
            String databaseName = "database";

            //when
            databaseInfo.createAndSaveURL(hostname, instance, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://\\MSSQLSERVER;databaseName=database");
        }

        @Test
        void createAndSaveURL_NoDatabaseName_NoException() {
            //given
            DatabaseInfo databaseInfo = new DatabaseInfo(SupportedDatabases.SQLSERVER);
            String hostname = "hostname";
            String instance = "MSSQLSERVER";
            String databaseName = "";

            //when
            databaseInfo.createAndSaveURL(hostname, instance, databaseName);

            //then
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://hostname\\MSSQLSERVER;databaseName=");
        }
    }
}