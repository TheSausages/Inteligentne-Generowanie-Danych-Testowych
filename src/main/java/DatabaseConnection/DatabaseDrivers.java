package DatabaseConnection;

public enum DatabaseDrivers {
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    MYSQL("com.mysql.cj.jdbc.Driver"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver")
    ;

    String driverString;

    DatabaseDrivers(String driver) {
        this.driverString = driver;
    }
}
