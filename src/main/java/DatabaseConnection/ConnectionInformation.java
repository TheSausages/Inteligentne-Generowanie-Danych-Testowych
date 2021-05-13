package DatabaseConnection;

import Exceptions.ConnectionException;
import TableMapping.TableMapper;
import TableMapping.TableMappingClass;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.*;



/**
 * Class responsible for the connection and executing all queries and updates in the database
 */
@Setter
@Getter
public class ConnectionInformation {

    /**
     * DataSource for the database. Created using information from {@link DatabaseInfo}
     */
    private HikariDataSource hikariDataSource;

    /**
     * The connection created from {@link HikariDataSource} fro the given database. Used to execute queries and updates
     */
    private Connection connection;

    /**
     * Class containing all necessary information about the database. See {@link DatabaseInfo}
     */
    private DatabaseInfo databaseInfo;

    /**
     * Class responsible for mapping the database tables and create a list of abstracts of them. See {@link TableMapper}
     */
    private TableMapper tableMapper;

    /**
     * Class Constructor
     * @param databaseInfo Class containing all necessary information about the database. See {@link DatabaseInfo}
     */
    public ConnectionInformation(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;

        this.tableMapper = new TableMapper();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseInfo.getDatabaseUrl());
        hikariConfig.setUsername(databaseInfo.getUsername());
        hikariConfig.setPassword(databaseInfo.getPassword());
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        try {
            hikariDataSource = new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            throw new ConnectionException("Please check if the inserted Data is correct:" + e.getMessage());
        }
    }

    /**
     * Use the {@link ConnectionInformation#connection} to open a connection to the database
     */
    public void connect() {
        try {
            connection = hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("There was a problem connecting to the database!");
        }
    }

    /**
     * Close the connection to the Database using {@link ConnectionInformation#connection}
     */
    public void closeConnection()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionException("Could not close connection!");
        }
    }

    /**
     * Use {@link ConnectionInformation#connection} to insert all the generated data to the database
     * @param inserts List of Insert statements that are used to insert the generated data. Created using {@link InsertCreation.InsertCreationClass}
     */
    public void insertsToDatabase(List<String> inserts) {
        try {
            if (databaseInfo.getSupportedDatabase() == SupportedDatabases.MYSQL) connection.setCatalog(databaseInfo.getDatabaseName());

            for (String insert : inserts) {
                System.out.println(insert);

                connection.createStatement().executeUpdate(insert);
            }
        } catch (SQLException e) {
            throw new ConnectionException("Error inserting data to the database:" + e.getMessage());
        }
    }

    /**
     * Selects the method for acquiring the database structure
     * @return List of {@link TableMappingClass} representing the tables in the database
     */
    public List<TableMappingClass> getTableInfo() {
        switch (databaseInfo.getSupportedDatabase()) {
            case ORACLE -> {
                return getTableResultOracle();
            }
            case SQLSERVER -> {
                return getTableResultSetSQLServer();
            }
            case MYSQL -> {
                return getTableResultSetMySql();
            }

            default -> throw new ConnectionException("This database is not supported!");
        }
    }

    /**
     * Method that, using {@link ConnectionInformation#connection}, reads the table structures in a MySQL database and maps them using {@link TableMapper#mapMySqlTable(List, SupportedDatabases)}
     * @return List of {@link TableMappingClass} representing the tables in the database
     */
    private List<TableMappingClass> getTableResultSetMySql() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet tableNames = connection.getMetaData().getTables(databaseInfo.getDatabaseName(), null, "%", new String[]{"TABLE"});

            List<ResultSet> tableInformationList = new ArrayList<>();
            while (tableNames.next()) {
                tableInformationList.add(connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.TableInformationMySQL.query, tableNames.getString(3))));
            }

            return tableMapper.mapMySqlTable(tableInformationList, databaseInfo.getSupportedDatabase());
        } catch (SQLException e) {
            throw new ConnectionException("Error connection to the database:" + e.getMessage());
        }
    }

    /**
     * Method that, using {@link ConnectionInformation#connection}, reads the table structures in a Oracle database and maps them using {@link TableMapper#mapOracleTable(Map, SupportedDatabases)}
     * @return List of {@link TableMappingClass} representing the tables in the database
     */
    private List<TableMappingClass> getTableResultOracle() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(DataSeizingSQLQueries.TableNamesOracle.query);

            Map<ResultSet, ResultSet> tableInformationList = new HashMap<>();
            StringBuilder tableName = new StringBuilder();
            while (resultSet.next()) {
                tableName.append(resultSet.getString(1));

                tableInformationList.put(connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
                                .executeQuery(String.format(DataSeizingSQLQueries.GetTableInformationOracle.query, tableName))
                        ,connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.GetTableConstraintsInformationOracle.query,tableName)));

                tableName.setLength(0);
            }

            return tableMapper.mapOracleTable(tableInformationList, databaseInfo.getSupportedDatabase());
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    /**
     * Method that, using {@link ConnectionInformation#connection}, reads the table structures in a SQL Server database and maps them using {@link TableMapper#mapSQLServerTable(Map, SupportedDatabases)}
     * @return List of {@link TableMappingClass} representing the tables in the database
     */
    private List<TableMappingClass> getTableResultSetSQLServer() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet tableNames = connection.createStatement().executeQuery(DataSeizingSQLQueries.GetTableNamesSQLServer.query);

            Map<ResultSet, ResultSet> tableInformationList = new HashMap<>();
            StringBuilder tableName = new StringBuilder();
            while (tableNames.next()) {
                tableName.append(tableNames.getString(1));

                tableInformationList.put(connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
                                .executeQuery(String.format(DataSeizingSQLQueries.GetTableInformationSQLServer.query, tableName))
                        , connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.GetTableConstraintsSQLServer.query, tableName)));

                tableName.setLength(0);
            }

            return tableMapper.mapSQLServerTable(tableInformationList, databaseInfo.getSupportedDatabase());
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }
}
