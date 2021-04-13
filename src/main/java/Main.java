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

        String column = "FirstName";
        String column1 = "SecondName";
        String column2 = "AAAAAAAA";

        System.out.println(ColumnNameMapping.getGenerator(column));
        System.out.println(ColumnNameMapping.getGenerator(column1));
        System.out.println(ColumnNameMapping.getGenerator(column2));
    }
}
