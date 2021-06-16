package GenerateInformation;

import DatabaseConnection.SupportedDatabases;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class GsonSettingsAdapterTest {

    @Mock
    private Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Settings.class, new GsonSettingsAdapter())
            .create();

    @Test
    void deserialize_allNulls_throwException() {
        //given
        String json = "{}";

        //when
        Exception exception = assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(exception.getMessage(), "No Database Type Was Selected!");
    }

    @Test
    void deserialize_OnlyDatabaseTypeMySQL_throwException() {
        //given
        String json = "{ \"databaseType\":MYSQL }";

        //when
        Exception exception = assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(exception.getMessage(), "No Database Name Was Selected!");
    }

    @Test
    void deserialize_OnlyDatabaseTypeORACLE_throwException() {
        //given
        String json = "{ \"databaseType\":ORACLE }";

        //when
        Exception exception = assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(exception.getMessage(), "No Database Name Was Selected!");
    }

    @Test
    void deserialize_OnlyDatabaseTypeSQLSERVER_throwException() {
        //given
        String json = "{ \"databaseType\":SQLSERVER }";

        //when
        Exception exception = assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(exception.getMessage(), "No Database Name Was Selected!");
    }

    @Test
    void deserialize_WithDatabaseTypeDatabaseName_throwException() {
        //given
        String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseName }";

        //when
        Exception exception = assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(exception.getMessage(), "No Username Was Selected!");
    }

    @Test
    void deserialize_WithDatabaseTypeDatabaseNameUsername_throwException() {
        //given
        String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseName, \"username\":system }";

        //when
        Exception exception = assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(exception.getMessage(), "No Password Was Selected!");
    }

    @Test
    void deserialize_IncorrectDatabaseType_throwException() {
        //given
        String json = "{ \"databaseType\":MYSQLNonValidName, \"databaseName\": TestDatabaseName, \"username\":system, \"password\":system  }";

        //when
        Exception exception = assertThrows(JsonSyntaxException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(ExceptionUtils.getRootCause(exception).getMessage(), "You must select a valid database!");
    }

    @Test
    void deserialize_IncorrectSeed_throwException() {
        //given
        String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseName, \"username\":system, \"password\":system, \"seed\":45ga  }";

        //when
        Exception exception = assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, Settings.class);
        });

        //then
        assertEquals(exception.getMessage(), "Seed should consist of numbers!");
    }

    @Nested
    @DisplayName("Settings Deserialization - MYSQL")
    class MySQL {
        @Test
        void deserialize_AllNecessaryData_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://localhost:3306");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataHostname_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system, \"hostname\":TestHostMYSQL }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://TestHostMYSQL:3306");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataPort_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system, \"port\":4532 }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://localhost:4532");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataSeed_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system, \"seed\":4532 }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://localhost:3306");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getSeed(), 4532L);
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataLocale_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system, \"locale\":en-US }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://localhost:3306");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "en-US");
        }

        @Test
        void deserialize_AllNecessaryDataTableMappingFilePath_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system, \"tableMappingFile\":\"folder\\\\textfile.txt\" }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://localhost:3306");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "folder\\textfile.txt");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataTableInsertFilePath_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system, \"insertFilePath\":\"folder\\\\textFile.txt\" }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://localhost:3306");
            assertEquals(settings.getInsertPath(), "folder\\textFile.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllData_NoErrors() {
            //given
            String json = "{ \"databaseType\":MYSQL, \"hostname\":TestHostMYSQL, \"port\":4532, \"databaseName\": TestDatabaseNameMYSQL, \"username\":system, \"password\":system, \"seed\":69420, \"locale\":pl-PL, \"tableMappingFile\":TestMapping.txt, \"insertFilePath\":TestInserts.txt }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.MYSQL);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameMYSQL");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:mysql://TestHostMYSQL:4532");
            assertEquals(settings.getInsertPath(), "TestInserts.txt");
            assertEquals(settings.getMappingDataPath(), "TestMapping.txt");
            assertEquals(settings.getSeed(), 69420);
            assertEquals(settings.getLocale(), "pl-PL");
        }
    }

    @Nested
    @DisplayName("Settings Deserialization - SQL Server")
    class SQLServer {
        @Test
        void deserialize_AllNecessaryData_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://localhost\\sqlexpress;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataHostname_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system, \"hostname\":TestHostMYSQL }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://TestHostMYSQL\\sqlexpress;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataPort_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system, \"port\":AnotherName }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://localhost\\AnotherName;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataSeed_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system, \"seed\":4532 }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://localhost\\sqlexpress;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getSeed(), 4532L);
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataLocale_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system, \"locale\":en-US }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://localhost\\sqlexpress;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "en-US");
        }

        @Test
        void deserialize_AllNecessaryDataTableMappingFilePath_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system, \"tableMappingFile\":\"folder\\\\textfile.txt\" }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://localhost\\sqlexpress;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "folder\\textfile.txt");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataTableInsertFilePath_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system, \"insertFilePath\":\"folder\\\\textFile.txt\" }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://localhost\\sqlexpress;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "folder\\textFile.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllData_NoErrors() {
            //given
            String json = "{ \"databaseType\":SQLSERVER, \"hostname\":TestHostSqlServer, \"port\":AnotherInstance, \"databaseName\": TestDatabaseNameSqlServer, \"username\":system, \"password\":system, \"seed\":69420, \"locale\":en-US, \"tableMappingFile\":TestMapping.txt, \"insertFilePath\":TestInserts.txt }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.SQLSERVER);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameSqlServer");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:sqlserver://TestHostSqlServer\\AnotherInstance;databaseName=TestDatabaseNameSqlServer");
            assertEquals(settings.getInsertPath(), "TestInserts.txt");
            assertEquals(settings.getMappingDataPath(), "TestMapping.txt");
            assertEquals(settings.getSeed(), 69420);
            assertEquals(settings.getLocale(), "en-US");
        }
    }

    @Nested
    @DisplayName("Settings Deserialization - Oracle")
    class Oracle {
        @Test
        void deserialize_AllNecessaryData_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataHostname_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system, \"hostname\":TestHostOracle }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@TestHostOracle:1521:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataPort_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system, \"port\":2585 }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@localhost:2585:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataSeed_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system, \"seed\":4532 }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getSeed(), 4532L);
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataLocale_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system, \"locale\":en-US }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "en-US");
        }

        @Test
        void deserialize_AllNecessaryDataTableMappingFilePath_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system, \"tableMappingFile\":\"folder\\\\textfile.txt\" }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "Inserts.txt");
            assertEquals(settings.getMappingDataPath(), "folder\\textfile.txt");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllNecessaryDataTableInsertFilePath_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system, \"insertFilePath\":\"folder\\\\textFile.txt\" }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@localhost:1521:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "folder\\textFile.txt");
            assertEquals(settings.getMappingDataPath(), "TableMapping.json");
            assertEquals(settings.getLocale(), "pl-PL");
        }

        @Test
        void deserialize_AllData_NoErrors() {
            //given
            String json = "{ \"databaseType\":ORACLE, \"hostname\":TestHostOracle, \"port\":6545, \"databaseName\": TestDatabaseNameOracle, \"username\":system, \"password\":system, \"seed\":69420, \"locale\":en-US, \"tableMappingFile\":TestMapping.txt, \"insertFilePath\":TestInserts.txt }";

            //when
            Settings settings = gson.fromJson(json, Settings.class);

            //then
            assertEquals(settings.getDatabaseInfo().getSupportedDatabase(), SupportedDatabases.ORACLE);
            assertEquals(settings.getDatabaseInfo().getDatabaseName(), "TestDatabaseNameOracle");
            assertEquals(settings.getDatabaseInfo().getDatabaseUrl(), "jdbc:oracle:thin:@TestHostOracle:6545:TestDatabaseNameOracle");
            assertEquals(settings.getInsertPath(), "TestInserts.txt");
            assertEquals(settings.getMappingDataPath(), "TestMapping.txt");
            assertEquals(settings.getSeed(), 69420);
            assertEquals(settings.getLocale(), "en-US");
        }
    }
}