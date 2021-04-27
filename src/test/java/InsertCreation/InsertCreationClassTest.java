package InsertCreation;

import DatabaseConnection.SupportedDatabases;
import TableMapping.ColumnMappingClass;
import TableMapping.Fields.NumberField;
import TableMapping.TableMappingClass;
import com.mysql.cj.xdevapi.Column;
import com.mysql.cj.xdevapi.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class InsertCreationClassTest {
    /*
    @Nested
    @DisplayName("List excitement DataCreationTest")
    class ListInfoTest {

         @Test
        void insertCreationClass_emptyarraylist() {
             //given
             List<TableMappingClass> mapping = new ArrayList<>();
             //String[][] list1 = {{"String","String"}, test1, test2};

             //when
             //String rslt = new InsertCreationClass().insertCreationClass(mapping, list1);

             //then
             //assertEquals("",rslt);
        }
        @Test
        void insertCreationClass_nonemptyarraylist() {
            //given
            List<TableMappingClass> mapping = new ArrayList<>();
            ColumnMappingClass column = ColumnMappingClass.builder()
                    .name("nazwisko")
                    .field(new NumberField())
                    .build();
            TableMappingClass table = TableMappingClass.builder()
                    .tableName("Baza1")
                    .tableType(SupportedDatabases.MYSQL)
                    .addColumn(column)
                    .build();

         //   String[][] list1 = {"test","test1", "test2"};

            //when
           // String rslt = new InsertCreationClass().insertCreationClass(mapping, list1);

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
            //String rslt = new InsertCreationClass().insertCreationClass(mapping, list1);

            //then
            //assertEquals("",rslt);
        }
}*/
}
