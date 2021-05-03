import GenerateInformation.IntelligentGeneration;

public class Main {
    public static void main(String[] args) {
        //launch(MainGui.class, args);
        /*
            Oracle: localhost, 1521, xe, system, system
            MySql: localhost, 3306, data1, system, system
            MSSQL: DESKTOP-MO1CJGE, SQLEXPRESS, proba, SystemProba, Password1
         */

        IntelligentGeneration generation = new IntelligentGeneration();
        //generation.launchGui(args);
        generation.getSettingsFromFile("");
        //generation.generateForMySQLDatabase("", "", "proba", "system", "system", 42069);
        //generation.generateForSQLServerDatabase("", "", "proba", "SystemProba", "Password1", 234598);
    }
}
