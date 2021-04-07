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
 * Class containing information about the connection to database
 */
@Setter
@Getter
public class ConnectionInformation {

    private HikariDataSource hikariDataSource;

    private Connection connection;

    private DatabaseInfo databaseInfo;

    private TableMapper tableMapper;

    public ConnectionInformation() {}

    /**
     * Creates A HikariDataSource from {@link DatabaseInfo}
     * @param databaseInfo Contains information on database like: user info, database url, database type
     */
    public void createDataSource(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;

        this.tableMapper = new TableMapper(databaseInfo);

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
            throw new ConnectionException("Please check if the inserted Data is correct!");
        }
    }

    public void connect() {
        try {
            connection = hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("There was a problem connecting to the database!");
        }
    }

    public void closeConnection()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionException("Could not close connection!");
        }
    }

    public List<TableMappingClass> getTableInfo() {
        switch (databaseInfo.getDatabaseDrivers()) {
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

    //MySQL section
    public List<TableMappingClass> getTableResultSetMySql() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet tableNames = connection.getMetaData().getTables(databaseInfo.getDatabaseName(), null, "%", new String[]{"TABLE"});

            List<ResultSet> tableInformationList = new ArrayList<>();

            while (tableNames.next()) {
                tableInformationList.add(connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.TableInformationMySQL.query, tableNames.getString(3))));
            }

            return tableMapper.mapMySqlTable(tableInformationList);
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    //OracleSection
    public List<TableMappingClass> getTableResultOracle() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(DataSeizingSQLQueries.TableNamesOracle.query);

            Map<ResultSet, ResultSet> tableInformationList = new HashMap<>();

            while (resultSet.next()) {
                String tableName = resultSet.getString(1);

                tableInformationList.put(connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.GetTableInformationOracle.query, tableName))
                        ,connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.GetTableConstraintsInformationOracle.query,tableName)));
            }

            return tableMapper.mapOracleTable(tableInformationList);
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    //SQLServer Section
    public List<TableMappingClass> getTableResultSetSQLServer() {
        try {
            connection.setCatalog(databaseInfo.getDatabaseName());

            ResultSet tableNames = connection.createStatement().executeQuery(DataSeizingSQLQueries.GetTableNamesSQLServer.query);

            Map<ResultSet, ResultSet> tableInformationList = new HashMap<>();

            while (tableNames.next()) {
                String tableName = tableNames.getString(1);

                tableInformationList.put(connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.GetTableInformationSQLServer.query, tableName))
                        , connection.createStatement().executeQuery(String.format(DataSeizingSQLQueries.GetTableConstraintsSQLServer.query, tableName)));
            }

            return tableMapper.mapSQLServerTable(tableInformationList);
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }
}
