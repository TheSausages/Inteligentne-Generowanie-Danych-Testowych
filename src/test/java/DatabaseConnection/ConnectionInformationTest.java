package DatabaseConnection;

import Exceptions.ConnectionException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionInformationTest {

    @Mock
    private Connection connection;

    @Test
    void setAccountInfo_CorrectData_NoError() {
        //given
        ConnectionInformation connectionInformation = new ConnectionInformation();
        String username = "user";
        String password = "password";

        //when
        connectionInformation.setAccountInfo(username, password);

        //then
        assertEquals(connectionInformation.getUsername(), username);
        assertEquals(connectionInformation.getPassword(), password);
    }

    @Test
    void setAccountInfo_NullUsername_ThrowError() {
        //given
        ConnectionInformation connectionInformation = new ConnectionInformation();
        String username = null;
        String password = "password";

        //when
        Exception exception = assertThrows(ConnectionException.class, () -> {
            connectionInformation.setAccountInfo(username, password);
        });

        //then
        assertEquals(exception.getMessage(), "Account data cannot be left blank!");
    }

    @Test
    void setAccountInfo_NullPassword_ThrowError() {
        //given
        ConnectionInformation connectionInformation = new ConnectionInformation();
        String username = "user";
        String password = null;

        //when
        Exception exception = assertThrows(ConnectionException.class, () -> {
            connectionInformation.setAccountInfo(username, password);
        });

        //then
        assertEquals(exception.getMessage(), "Account data cannot be left blank!");
    }

    @Nested()
    @DisplayName("MySql URL")
    class MySqlURL {
        @Test
        void createAndSaveURL_MySqlAllData_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL);
            String hostname = "localhost";
            String port = "3306";
            String databaseName = "proba";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:mysql://localhost:3306/proba");
        }

        @Test
        void createAndSaveURL_MySqlNoHostName_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL);
            String hostname = "";
            String port = "3306";
            String databaseName = "proba";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:mysql://:3306/proba");
        }

        @Test
        void createAndSaveURL_MySqlNoPort_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL);
            String hostname = "localhost";
            String port = "";
            String databaseName = "proba";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:mysql://localhost:/proba");
        }

        @Test
        void createAndSaveURL_MySqlNoDatabaseName_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL);
            String hostname = "localhost";
            String port = "3306";
            String databaseName = "";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:mysql://localhost:3306/");
        }
    }

    @Nested
    @DisplayName("Oracle URL")
    class OracleURL {
        @Test
        void createAndSaveURL_OracleAllData_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE);
            String hostname = "localhost";
            String port = "1521";
            String databaseName = "xe";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:xe");
        }

        @Test
        void createAndSaveURL_OracleNoHostname_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE);
            String hostname = "";
            String port = "1521";
            String databaseName = "xe";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:oracle:thin:@:1521:xe");
        }

        @Test
        void createAndSaveURL_OracleNoPort_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE);
            String hostname = "localhost";
            String port = "";
            String databaseName = "xe";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:oracle:thin:@localhost::xe");
        }

        @Test
        void createAndSaveURL_OracleNoDatabaseName_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE);
            String hostname = "localhost";
            String port = "1521";
            String databaseName = "";

            //when
            connectionInformation.createAndSaveURL(hostname, port, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:");
        }
    }

    @Nested
    @DisplayName("SQL Server URL")
    class SQLServerURL {
        @Test
        void createAndSaveURL_SQLServerAllData_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER);
            String serverName = "DESKTOP-MO1CJGE";
            String instanceName = "SQLEXPRESS";
            String databaseName = "proba";

            //when
            connectionInformation.createAndSaveURL(serverName, instanceName, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:sqlserver://DESKTOP-MO1CJGE\\SQLEXPRESS;databaseName=proba");
        }

        @Test
        void createAndSaveURL_SQLServerNoServerName_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER);
            String serverName = "";
            String instanceName = "SQLEXPRESS";
            String databaseName = "proba";

            //when
            connectionInformation.createAndSaveURL(serverName, instanceName, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:sqlserver://\\SQLEXPRESS;databaseName=proba");
        }

        @Test
        void createAndSaveURL_SQLServerNoInstanceName_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER);
            String serverName = "DESKTOP-MO1CJGE";
            String instanceName = "";
            String databaseName = "proba";

            //when
            connectionInformation.createAndSaveURL(serverName, instanceName, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:sqlserver://DESKTOP-MO1CJGE\\;databaseName=proba");
        }

        @Test
        void createAndSaveURL_SQLServerNoDatabaseName_NoError() {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER);
            String serverName = "DESKTOP-MO1CJGE";
            String instanceName = "SQLEXPRESS";
            String databaseName = "";

            //when
            connectionInformation.createAndSaveURL(serverName, instanceName, databaseName);

            //then
            assertEquals(connectionInformation.getDatabaseUrl(), "jdbc:sqlserver://DESKTOP-MO1CJGE\\SQLEXPRESS;databaseName=");
        }
    }

    @Nested
    @DisplayName("MySql Connection Test to database with parameters:\nDatabase Name:proba\nHostname:localhost\nPort:3306\nLogin Username:system\nLogin Password:system")
    class MySqlConnection {
        @Test
        void createAndSaveURL_MySqlAllData_NoError() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "3306", "proba");

            //when
            connectionInformation.connect();

            //then
            assertTrue(connectionInformation.getConnection().isValid(1));
        }

        @Test
        void createAndSaveURL_MySqlWrongHost_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL, "system", "system");
            connectionInformation.createAndSaveURL("wrongHost", "3306", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the MYSQL database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_MySqlWrongPort_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "1", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the MYSQL database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_MySqlWrongDatabaseName_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "3306", "NoName");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the MYSQL database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_MySqlWrongUsername_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL, "USERNAME", "system");
            connectionInformation.createAndSaveURL("localhost", "3306", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the MYSQL database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_MySqlWrongPassword_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL, "system", "PASSWORD");
            connectionInformation.createAndSaveURL("localhost", "3306", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the MYSQL database!", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Oracle Connection Test to database with parameters:\nDatabase Name:xe\nHostname:localhost\nPort:1521\nLogin Username:system\nLogin Password:system")
    class OracleConnection {
        @Test
        void createAndSaveURL_OracleAllData_NoError() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "1521", "xe");

            //when
            connectionInformation.connect();

            //then
            assertTrue(connectionInformation.getConnection().isValid(1));
        }

        @Test
        void createAndSaveURL_OracleWrongHost_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE, "system", "system");
            connectionInformation.createAndSaveURL("wrongHost", "1521", "xe");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the ORACLE database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_OracleWrongPort_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "1", "xe");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the ORACLE database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_OracleWrongDatabaseName_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "1521", "NoName");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the ORACLE database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_OracleWrongUsername_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE, "USERNAME", "system");
            connectionInformation.createAndSaveURL("localhost", "1521", "xe");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the ORACLE database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_OracleWrongPassword_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE, "system", "PASSWORD");
            connectionInformation.createAndSaveURL("localhost", "1521", "xe");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the ORACLE database!", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("SQLSERVER Connection Test to database with parameters:\nDatabase Name:proba\nServer Name:DESKTOP-MO1CJGE\nInstance Name:SQLEXPRESS\nLogin Username:SystemProba\nLogin Password:Password1")
    class SQLSERVERConnection {
        @Test
        void createAndSaveURL_SQLSERVERAllData_NoError() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "SystemProba", "Password1");
            connectionInformation.createAndSaveURL("DESKTOP-MO1CJGE", "SQLEXPRESS", "proba");

            //when
            connectionInformation.connect();

            //then
            assertTrue(connectionInformation.getConnection().isValid(1));
        }

        @Test
        void createAndSaveURL_SQLSERVERWrongHost_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "SystemProba", "Password1");
            connectionInformation.createAndSaveURL("wrongHost", "SQLEXPRESS", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the SQLSERVER database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_SQLSERVERWrongPort_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "SystemProba", "Password1");
            connectionInformation.createAndSaveURL("DESKTOP-MO1CJGE", "1", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the SQLSERVER database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_SQLSERVERWrongDatabaseName_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "SystemProba", "Password1");
            connectionInformation.createAndSaveURL("DESKTOP-MO1CJGE", "SQLEXPRESS", "NoName");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the SQLSERVER database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_SQLSERVERWrongUsername_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "USERNAME", "Password1");
            connectionInformation.createAndSaveURL("DESKTOP-MO1CJGE", "SQLEXPRESS", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the SQLSERVER database!", exception.getMessage());
        }

        @Test
        void createAndSaveURL_SQLSERVERWrongPassword_ThrowException() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "SystemProba", "PASSWORD");
            connectionInformation.createAndSaveURL("DESKTOP-MO1CJGE", "SQLEXPRESS", "proba");

            //when
            Exception exception = assertThrows(ConnectionException.class, connectionInformation::connect);

            //then
            assertEquals("There was a problem connecting to the SQLSERVER database!", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Check database connection closing")
    class ConnectionClosing {
        @Test
        void closeConnection_MySQL_NoErrors() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.MYSQL, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "3306", "proba");
            connectionInformation.connect();

            //when
            connectionInformation.closeConnection();

            //then
            assertTrue(connectionInformation.getConnection().isClosed());
        }

        @Test
        void closeConnection_Oracle_NoErrors() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.ORACLE, "system", "system");
            connectionInformation.createAndSaveURL("localhost", "1521", "xe");
            connectionInformation.connect();

            //when
            connectionInformation.closeConnection();

            //then
            assertTrue(connectionInformation.getConnection().isClosed());
        }

        @Test
        void closeConnection_SQLServer_NoErrors() throws SQLException {
            //given
            ConnectionInformation connectionInformation = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "SystemProba", "Password1");
            connectionInformation.createAndSaveURL("DESKTOP-MO1CJGE", "SQLEXPRESS", "proba");
            connectionInformation.connect();

            //when
            connectionInformation.closeConnection();

            //then
            assertTrue(connectionInformation.getConnection().isClosed());
        }
    }
}