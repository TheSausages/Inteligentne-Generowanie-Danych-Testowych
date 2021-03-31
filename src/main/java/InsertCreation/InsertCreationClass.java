package InsertCreation;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import DatabaseConnection.SupportedDatabases;
import TableMapping.ColumnMappingClass;

import TableMapping.TableMappingClass;


public class InsertCreationClass {
    public static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }


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

        mappedTables.forEach(tableMappingClass -> {
            System.out.println("INSERT INTO ");
            System.out.printf(tableMappingClass.getTableName());
            System.out.printf(" (");
            tableMappingClass.getColumns().forEach(columnMappingClass -> {
                        System.out.printf(columnMappingClass.getName());
                        System.out.printf(",");

                    });
            System.out.println(")");
            System.out.printf("VALUES ('");
            System.out.println("....','....','....','....')");
        });

        var ref = new Object() {
            String str1="";
            String str2="";
            String str3="";
        };

        try {
            File newTextFile = new File("thetextfile.txt");
            FileWriter fw = new FileWriter(newTextFile);
        mappedTables.forEach(tableMappingClass -> {
                    ref.str1 = "INSERT INTO " + tableMappingClass.getTableName() + " (";
            tableMappingClass.getColumns().forEach(columnMappingClass -> {
                    ref.str2 = ref.str2 + columnMappingClass.getName()+",";
            });
            ref.str3 = ") VALUES ('....','....','....','....');";
            try {
                fw.write(ref.str1+removeLastCharacter(ref.str2)+ ref.str3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ref.str2="";
        });
            fw.close();
        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }
}
