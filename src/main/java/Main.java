import DataCreation.ColumnNameMapping;
import Gui.MainGui;
import TableMapping.ColumnMappingClass;
import org.apache.commons.lang3.StringUtils;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args)  {
         //launch(MainGui.class, args);
        /*
            Oracle: localhost, 1521, xe, system, system
            MySql: localhost, 3306, data1, system, system
            MSSQL: DESKTOP-MO1CJGE, SQLEXPRESS, proba, SystemProba, Password1
         */

        IntelligentGeneration generation = new IntelligentGeneration();
        generation.generateForMySQLDatabase("","","probna","system","system", 2, 69420);
    }
}
