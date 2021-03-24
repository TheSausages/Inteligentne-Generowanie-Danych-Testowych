import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseDrivers;

public class Main {
    public static void main(String[] args) {
        System.out.println("Establish Connections:");

        ConnectionInformation cInfOracle = new ConnectionInformation(DatabaseDrivers.ORACLE, "system", "system");
        cInfOracle.createAndSaveURL("localhost", "1521", "xe");
        cInfOracle.connect();
        cInfOracle.closeConnection();

        ConnectionInformation cInfMySql = new ConnectionInformation(DatabaseDrivers.MYSQL, "system", "system");
        cInfMySql.createAndSaveURL("localhost", "3306", "proba");
        cInfMySql.connect();
        cInfMySql.closeConnection();

        ConnectionInformation cInfMSSql = new ConnectionInformation(DatabaseDrivers.SQLSERVER, "SystemProba", "Password1");
        cInfMSSql.createAndSaveURL("DESKTOP-MO1CJGE", "SQLEXPRESS", "proba");
        cInfMSSql.connect();
        cInfMSSql.closeConnection();
    }
}
