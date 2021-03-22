import DatabaseConnection.ConnectionInformation;

public class Main {
    public static void main(String[] args) {
        System.out.println("Establish Connections:");

        ConnectionInformation cInfOracle = new ConnectionInformation("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
        cInfOracle.connectOracle();
        cInfOracle.closeConnection();

        ConnectionInformation cInfMySql = new ConnectionInformation("jdbc:mysql://localhost:3306/proba", "system", "system");
        cInfMySql.connectMySql();
        cInfMySql.closeConnection();

        ConnectionInformation cInfMSSql = new ConnectionInformation("jdbc:sqlserver://DESKTOP-MO1CJGE\\SQLEXPRESS;databaseName=proba", "SystemProba", "Password1");
        cInfMSSql.connectSqlServer();
        cInfMSSql.closeConnection();
    }
}
