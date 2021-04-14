package InsertCreation;

import DatabaseConnection.ConnectionInformation;
import DatabaseConnection.DatabaseInfo;
import Exceptions.ConnectionException;
import TableMapping.TableMappingClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class InsertCreationClassTest {
    @Nested
    @DisplayName("List excitement DataCreationTest")
    class ListInfoTest {

         @Test
        void insertCreationClass_emptyarraylist() {
             //given
             List<TableMappingClass> mapping = new ArrayList<>();
             String[] test = new String[4];
             test[0]="this";
             test[1]="is";
             test[2]="testing";
             test[3]="string";
             String[] test1 = new String[4];
             test1[0]="THIS";
             test1[1]="IS";
             test1[2]="TESTING";
             test1[3]="STRING";
             String[] test2 = new String[4];
             test2[0]="THISS";
             test2[1]="ISS";
             test2[2]="TESTINGG";
             test2[3]="STRINGG";
             String[][] list1 = {test, test1, test2};

             //when
             String rslt = new InsertCreationClass().InsertCreationClass(mapping, list1);

             //then
             assertEquals("",rslt);
        }
        @Test
        void insertCreationClass_nonemptyarraylist() {
            //give
            List<TableMappingClass> mapping = new ArrayList<>();//        List<TableMappingClass> mappedTables = new ArrayList<>();
//        ColumnMappingClass nazwisko = new ColumnMappingClass();
//        nazwisko.setName("nazwisko");
//        ColumnMappingClass imie = new ColumnMappingClass();
//        imie.setName("imie");
//        ColumnMappingClass samochod = new ColumnMappingClass();
//        samochod.setName("samochod");
//        ColumnMappingClass numer = new ColumnMappingClass();
//        numer.setName("numer");
//        TableMappingClass pierwsza =  new TableMappingClass();
//        pierwsza.setTableName("Baza1");
//        pierwsza.setTableType(SupportedDatabases.SQLSERVER);
//        pierwsza.addColumn(nazwisko);
//        pierwsza.addColumn(imie);
//        pierwsza.addColumn(samochod);
//        pierwsza.addColumn(numer);
//        mappedTables.add(pierwsza);
            String[] test = new String[4];
            test[0]="this";
            test[1]="is";
            test[2]="testing";
            test[3]="string";
            String[] test1 = new String[4];
            test1[0]="THIS";
            test1[1]="IS";
            test1[2]="TESTING";
            test1[3]="STRING";
            String[] test2 = new String[4];
            test2[0]="THISS";
            test2[1]="ISS";
            test2[2]="TESTINGG";
            test2[3]="STRINGG";
            String[][] list1 = {test,test1, test2};

            //when
            String rslt = new InsertCreationClass().InsertCreationClass(mapping, list1);

            //then
            //assertEquals("INSERT INTO Baza1 (nazwisko,imie,samochod,numer) VALUES ('this','THIS','THISS'),('is','IS','ISS'),('testing','TESTING','TESTINGG'),('string','STRING','STRINGG');",rslt);
        }
        @Test
        void insertCreationClass_emptystringtables() {
            //given
            List<TableMappingClass> mapping = new ArrayList<>();
            //mapping.add("");
            String[] test = new String[4];
            String[] test1 = new String[4];
            String[] test2 = new String[4];
            String[][] list1 = {test,test1, test2};

            //when
            String rslt = new InsertCreationClass().InsertCreationClass(mapping, list1);

            //then
            assertEquals("",rslt);
        }
}
}
