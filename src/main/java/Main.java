import DatabaseConnection.ConnectionInformation;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Establish Connection");

        ConnectionInformation cinf = new ConnectionInformation("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "system");

        cinf.connect();
    }
}
