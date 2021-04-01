import DatabaseConnection.SupportedDatabases;
import Gui.MainGui;
import InsertCreation.InsertCreationClass;
import TableMapping.ColumnMappingClass;
import TableMapping.TableMappingClass;

import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args)  {
        launch(MainGui.class, args);

        /*
            Oracle: localhost, 1521, xe, system, system
            MySql: localhost, 3306, data1, system, system
            MSSQL: DESKTOP-MO1CJGE, SQLEXPRESS, proba, SystemProba, Password1
         */
    }
}
