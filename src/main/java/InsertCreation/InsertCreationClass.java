package InsertCreation;
import java.io.*;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

import DatabaseConnection.SupportedDatabases;
import TableMapping.ColumnMappingClass;
import TableMapping.Field;
import TableMapping.ForeignKeyMapping;
import TableMapping.TableMappingClass;
import jdk.jfr.Unsigned;

public class InsertCreationClass {
    public void InsertCreationClass() {

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



//        mappedTables.forEach(tableMappingClass -> {
//            System.out.println("INSERT INTO ");
//            System.out.printf(tableMappingClass.getTableName());
//            System.out.printf(" (");
//            tableMappingClass.getColumns().forEach(columnMappingClass -> {
//                        System.out.printf(columnMappingClass.getName());
//                        System.out.printf(",");
//                    });
//            System.out.println(")");
//            System.out.printf("VALUES ('");
//            System.out.println("....','....','....','....')");
//        });



        try {
            File newTextFile = new File("C:/thetextfile.txt");
            FileWriter fw = new FileWriter(newTextFile);
        mappedTables.forEach(tableMappingClass -> {
                    String str1 = "INSERT INTO " + tableMappingClass.getTableName() + " (";
            try {
                fw.write(str1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tableMappingClass.getColumns().forEach(columnMappingClass -> {
                    String str2 = columnMappingClass.getName()+",";
                try {
                    fw.write(str2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
                 String str3 = ") VALUES ('....','....','....','....');";
            try {
                fw.write(str3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
            fw.close();

       } catch (IOException iox) {
            iox.printStackTrace();
        }

//        String str1 = "INSERT INTO table_name ("+jeden.getName()+","+dwa.getName()+","+trzy.getName()+","+cztery.getName()+") VALUES ('"+one.getNamee()+"','"+one.getSurname()+"','"+one.getCar()+"','"+one.getNumber()+"');";
//        String str2 = "INSERT INTO table_name ("+jeden.getName()+","+dwa.getName()+","+trzy.getName()+","+cztery.getName()+") VALUES ('"+two.getNamee()+"','"+two.getSurname()+"','"+two.getCar()+"','"+two.getNumber()+"');";
//


//        try {
//            File newTextFile = new File("C:/thetextfile.txt");
//            FileWriter fw = new FileWriter(newTextFile);
//            fw.write(st);
//            fw.close();
//
//        } catch (IOException iox) {
//            iox.printStackTrace();
//        }

        //INSERT INTO table_name (column1, column2, column3, ...)
        //VALUES (value1, value2, value3, ...);
        //System.out.printf("insert");
    }
}
