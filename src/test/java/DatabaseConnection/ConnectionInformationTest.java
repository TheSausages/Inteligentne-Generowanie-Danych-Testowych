package DatabaseConnection;

import Exceptions.ConnectionException;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionInformationTest {

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
        Exception exception = assertThrows(ConnectionException.class, () -> connectionInformation.setAccountInfo(username, password));

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
        Exception exception = assertThrows(ConnectionException.class, () -> connectionInformation.setAccountInfo(username, password));

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
}