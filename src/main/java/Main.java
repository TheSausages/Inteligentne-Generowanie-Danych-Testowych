import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseDrivers;

public class Main {
    public static void main(String[] args) {
        System.out.println("Establish Connections:");

        ConnectionInformation cInfOracle = new ConnectionInformation(DatabaseDrivers.ORACLE, "jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
        cInfOracle.connect();
        cInfOracle.closeConnection();

        ConnectionInformation cInfMySql = new ConnectionInformation(DatabaseDrivers.MYSQL, "jdbc:mysql://localhost:3306/proba", "system", "system");
        cInfMySql.connect();
        cInfMySql.closeConnection();

        ConnectionInformation cInfMSSql = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "jdbc:sqlserver://DESKTOP-MO1CJGE\\SQLEXPRESS;databaseName=proba", "SystemProba", "Password1");
        cInfMSSql.connect();
        cInfMSSql.closeConnection();

    }
}
