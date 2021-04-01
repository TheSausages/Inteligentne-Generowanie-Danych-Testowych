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
       // launch(MainGui.class, args);
        List<TableMappingClass> mappedTables = new ArrayList<>();
        ColumnMappingClass nazwisko = new ColumnMappingClass();
        nazwisko.setName("nazwisko");
        ColumnMappingClass imie = new ColumnMappingClass();
        imie.setName("imie");
        ColumnMappingClass samochod = new ColumnMappingClass();
        samochod.setName("samochod");
        ColumnMappingClass numer = new ColumnMappingClass();
        numer.setName("numer");
        TableMappingClass pierwsza =  new TableMappingClass();
        pierwsza.setTableName("Baza1");
        pierwsza.setTableType(SupportedDatabases.SQLSERVER);
        pierwsza.addColumn(nazwisko);
        pierwsza.addColumn(imie);
        pierwsza.addColumn(samochod);
        pierwsza.addColumn(numer);
        mappedTables.add(pierwsza);

        TableMappingClass druga =  new TableMappingClass();
        druga.setTableName("Baza2");
        druga.setTableType(SupportedDatabases.MYSQL);
        druga.addColumn(nazwisko);
        druga.addColumn(imie);
        druga.addColumn(samochod);
        druga.addColumn(numer);
        mappedTables.add(druga);
        new InsertCreationClass().InsertCreationClass(mappedTables);

        /*
            Oracle: localhost, 1521, xe, system, system
            MySql: localhost, 3306, data1, system, system
            MSSQL: DESKTOP-MO1CJGE, SQLEXPRESS, proba, SystemProba, Password1
         */
    }
}
