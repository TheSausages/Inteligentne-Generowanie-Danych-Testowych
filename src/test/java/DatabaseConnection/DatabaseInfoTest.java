package DatabaseConnection;


import Exceptions.ConnectionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseInfoTest {
    @Test
    void builder_NoInformationGiven_ThrowException() {
        //given
        DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder();

        //when
        Exception exception  = assertThrows(IllegalStateException.class, builder::build);

        //then
        assertEquals(exception.getMessage(), "You must select a database!");
    }

    @Nested
    @DisplayName("DatabaseInfo Tests - Oracle Database")
    class Oracle {
        @Test
        void builder_OnlyDatabaseType_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE);

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Database name cannot be empty!");
        }

        @Test
        void builder_NoAccountCredentials_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE)
                    .name("DatabaseNameOracle");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_NoPassword_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE)
                    .name("DatabaseNameOracle")
                    .username("UsernameOracle");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_NoUsername_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE)
                    .name("DatabaseNameOracle")
                    .password("PasswordOracle");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_CorrectDataOracleNoHostNameNoPort_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE)
                    .name("DatabaseNameOracle")
                    .username("UsernameOracle")
                    .password("PasswordOracle");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameOracle");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:DatabaseNameOracle");
            assertEquals(databaseInfo.getUsername(), "UsernameOracle");
            assertEquals(databaseInfo.getPassword(), "PasswordOracle");
        }

        @Test
        void builder_CorrectDataOracleNoPort_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE)
                    .hostOrServerName("HostNameOracle")
                    .name("DatabaseNameOracle")
                    .username("UsernameOracle")
                    .password("PasswordOracle");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameOracle");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@HostNameOracle:1521:DatabaseNameOracle");
            assertEquals(databaseInfo.getUsername(), "UsernameOracle");
            assertEquals(databaseInfo.getPassword(), "PasswordOracle");
        }

        @Test
        void builder_CorrectDataOracleNoHostname_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE)
                    .portOrInstance("7896")
                    .name("DatabaseNameOracle")
                    .username("UsernameOracle")
                    .password("PasswordOracle");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameOracle");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@localhost:7896:DatabaseNameOracle");
            assertEquals(databaseInfo.getUsername(), "UsernameOracle");
            assertEquals(databaseInfo.getPassword(), "PasswordOracle");
        }

        @Test
        void builder_CorrectDataOracleAll_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.ORACLE)
                    .hostOrServerName("HostNameOracle")
                    .portOrInstance("7896")
                    .name("DatabaseNameOracle")
                    .username("UsernameOracle")
                    .password("PasswordOracle");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameOracle");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:oracle:thin:@HostNameOracle:7896:DatabaseNameOracle");
            assertEquals(databaseInfo.getUsername(), "UsernameOracle");
            assertEquals(databaseInfo.getPassword(), "PasswordOracle");
        }
    }

    @Nested
    @DisplayName("DatabaseInfo Tests - MySQL Database")
    class MySQL {
        @Test
        void builder_OnlyDatabaseType_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL);

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Database name cannot be empty!");
        }

        @Test
        void builder_NoAccountCredentials_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL)
                    .name("DatabaseNameMySQL");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_NoPassword_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL)
                    .name("DatabaseNameMySQL")
                    .username("UsernameMySQL");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_NoUsername_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL)
                    .name("DatabaseNameMySQL")
                    .password("PasswordMySQL");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_CorrectDataOracleNoHostNameNoPort_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL)
                    .name("DatabaseNameMySQL")
                    .username("UsernameMySQL")
                    .password("PasswordMySQL");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameMySQL");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://localhost:3306");
            assertEquals(databaseInfo.getUsername(), "UsernameMySQL");
            assertEquals(databaseInfo.getPassword(), "PasswordMySQL");
        }

        @Test
        void builder_CorrectDataOracleNoPort_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL)
                    .hostOrServerName("HostNameMySQL")
                    .name("DatabaseNameMySQL")
                    .username("UsernameMySQL")
                    .password("PasswordMySQL");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameMySQL");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://HostNameMySQL:3306");
            assertEquals(databaseInfo.getUsername(), "UsernameMySQL");
            assertEquals(databaseInfo.getPassword(), "PasswordMySQL");
        }

        @Test
        void builder_CorrectDataOracleNoHostname_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL)
                    .portOrInstance("2345")
                    .name("DatabaseNameMySQL")
                    .username("UsernameMySQL")
                    .password("PasswordMySQL");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameMySQL");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://localhost:2345");
            assertEquals(databaseInfo.getUsername(), "UsernameMySQL");
            assertEquals(databaseInfo.getPassword(), "PasswordMySQL");
        }

        @Test
        void builder_CorrectDataOracleAll_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.MYSQL)
                    .hostOrServerName("HostNameMySQL")
                    .portOrInstance("2345")
                    .name("DatabaseNameMySQL")
                    .username("UsernameMySQL")
                    .password("PasswordMySQL");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameMySQL");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:mysql://HostNameMySQL:2345");
            assertEquals(databaseInfo.getUsername(), "UsernameMySQL");
            assertEquals(databaseInfo.getPassword(), "PasswordMySQL");
        }
    }

    @Nested
    @DisplayName("DatabaseInfo Tests - SQL Server Database")
    class SQLServer {
        @Test
        void builder_OnlyDatabaseType_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER);

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Database name cannot be empty!");
        }

        @Test
        void builder_NoAccountCredentials_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER)
                    .name("DatabaseNameSQLServer");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_NoPassword_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER)
                    .name("DatabaseNameSQLServer")
                    .username("UsernameSQLServer");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_NoUsername_ThrowException() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER)
                    .name("DatabaseNameSQLServer")
                    .password("PasswordSQLServer");

            //when
            Exception exception  = assertThrows(IllegalStateException.class, builder::build);

            //then
            assertEquals(exception.getMessage(), "Account Credentials cannot be empty!");
        }

        @Test
        void builder_CorrectDataOracleNoHostNameNoPort_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER)
                    .name("DatabaseNameSQLServer")
                    .username("UsernameSQLServer")
                    .password("PasswordSQLServer");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameSQLServer");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://localhost\\sqlexpress;databaseName=DatabaseNameSQLServer");
            assertEquals(databaseInfo.getUsername(), "UsernameSQLServer");
            assertEquals(databaseInfo.getPassword(), "PasswordSQLServer");
        }

        @Test
        void builder_CorrectDataOracleNoPort_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER)
                    .hostOrServerName("HostNameSQLServer")
                    .name("DatabaseNameSQLServer")
                    .username("UsernameSQLServer")
                    .password("PasswordSQLServer");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameSQLServer");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://HostNameSQLServer\\sqlexpress;databaseName=DatabaseNameSQLServer");
            assertEquals(databaseInfo.getUsername(), "UsernameSQLServer");
            assertEquals(databaseInfo.getPassword(), "PasswordSQLServer");
        }

        @Test
        void builder_CorrectDataOracleNoHostname_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER)
                    .portOrInstance("SQLInstance")
                    .name("DatabaseNameSQLServer")
                    .username("UsernameSQLServer")
                    .password("PasswordSQLServer");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameSQLServer");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://localhost\\SQLInstance;databaseName=DatabaseNameSQLServer");
            assertEquals(databaseInfo.getUsername(), "UsernameSQLServer");
            assertEquals(databaseInfo.getPassword(), "PasswordSQLServer");
        }

        @Test
        void builder_CorrectDataOracleAll_NoErrors() {
            //given
            DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder()
                    .database(SupportedDatabases.SQLSERVER)
                    .hostOrServerName("HostNameSQLServer")
                    .portOrInstance("SQLInstance")
                    .name("DatabaseNameSQLServer")
                    .username("UsernameSQLServer")
                    .password("PasswordSQLServer");

            //when
            DatabaseInfo databaseInfo = builder.build();

            //then
            assertEquals(databaseInfo.getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(databaseInfo.getDatabaseName(), "DatabaseNameSQLServer");
            assertEquals(databaseInfo.getDatabaseUrl(), "jdbc:sqlserver://HostNameSQLServer\\SQLInstance;databaseName=DatabaseNameSQLServer");
            assertEquals(databaseInfo.getUsername(), "UsernameSQLServer");
            assertEquals(databaseInfo.getPassword(), "PasswordSQLServer");
        }
    }
}