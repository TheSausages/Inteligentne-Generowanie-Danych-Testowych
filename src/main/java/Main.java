import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseDrivers;

public class Main {
    public static void main(String[] args) {
        System.out.println("Establish Connections:");

        ConnectionInformation cInfOracle = new ConnectionInformation(DatabaseDrivers.ORACLE);
        cInfOracle.createAndSaveURL("localhost", "1521", "xe");
        cInfOracle.setAccountInfo("system", "system");
        cInfOracle.connect();
        cInfOracle.closeConnection();

        ConnectionInformation cInfMySql = new ConnectionInformation(DatabaseDrivers.MYSQL);
        cInfMySql.createAndSaveURL("localhost", "3306", "proba");
        cInfMySql.setAccountInfo("system", "system");
        cInfMySql.connect();
        cInfMySql.closeConnection();

        ConnectionInformation cInfMSSql = new ConnectionInformation(DatabaseDrivers.SQLSERVER);
        cInfMSSql.createAndSaveURL("DESKTOP-MO1CJGE", "SQLEXPRESS", "proba");
        cInfMSSql.setAccountInfo("SystemProba", "Password1");
        cInfMSSql.connect();
        cInfMSSql.closeConnection();
    }
}
