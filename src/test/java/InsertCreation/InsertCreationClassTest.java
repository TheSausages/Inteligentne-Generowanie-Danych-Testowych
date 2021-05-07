package InsertCreation;

import DataCreation.ColumnNameMapping;
import DatabaseConnection.SupportedDatabases;
import TableMapping.ColumnMappingClass;
import TableMapping.Fields.Field;
import TableMapping.Fields.NumberField;
import TableMapping.Fields.TextField;
import TableMapping.TableMappingClass;
import com.mysql.cj.xdevapi.Column;
import com.mysql.cj.xdevapi.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
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
            String[][] nowy = {{"Kacper","Krzysztof","Piotr","Maciej","Zbigniew"},{"Anand","Fischer","Doe","Kowalski","Salah"}};
            List <String[][]> list1 = new ArrayList<>();
            list1.add(nowy);

            //when
            List<String> rslt = new InsertCreationClass().insertCreationClass(mapping, list1);

            //then
            assertEquals("",rslt);
        }
        @Test
        void insertCreationClass_nonemptyarraylist() {
            //given
            Field field = new TextField();
            field.setFieldInfo(new String[]{"tekst"});

            List<TableMappingClass> list = new ArrayList<>();
            ColumnMappingClass column1 = ColumnMappingClass.builder()
                    .name("firstname")
                    .field(field)
                    .build();
            ColumnMappingClass column2 = ColumnMappingClass.builder()
                    .name("lastname")
                    .field(field)
                    .build();
            TableMappingClass mapping = TableMappingClass.builder()
                    .tableName("Baza1")
                    .tableType(SupportedDatabases.MYSQL)
                    .addColumn(column1)
                    .addColumn(column2)
                    .build();
            mapping.setNumberOfGenerations(4);
            list.add(mapping);

            String[][] nowy = {{"Kacper","Krzysztof","Piotr","Maciej"},{"Anand","Fischer","Doe","Kowalski"}};
            List <String[][]> list1 = new ArrayList<>();
            list1.add(nowy);

            //when
            List<String> rslt = new InsertCreationClass().insertCreationClass(list, list1);

            //then
            assertEquals("INSERT INTO Baza1 (firstname,lastname) VALUES ('Kacper','Anand'),('Krzysztof','Fischer'),('Piotr','Doe'),('Maciej','Kowalski');",rslt);
        }
        @Test
        void insertCreationClass_emptystringtables() {
            //given
            Field field = new TextField();
            field.setFieldInfo(new String[]{"int"});

            List<TableMappingClass> list = new ArrayList<>();
            ColumnMappingClass column1 = ColumnMappingClass.builder()
                    .name("firstname")
                    .field(field)
                    .build();
            ColumnMappingClass column2 = ColumnMappingClass.builder()
                    .name("lastname")
                    .field(field)
                    .build();
            TableMappingClass mapping = TableMappingClass.builder()
                    .tableName("Baza1")
                    .tableType(SupportedDatabases.MYSQL)
                    .addColumn(column1)
                    .addColumn(column2)
                    .build();
            mapping.setNumberOfGenerations(5);
            list.add(mapping);;

            String[][] nowy = {{"","","","",""},{"","","","",""}};
            List <String[][]> list1 = new ArrayList<>();
            list1.add(nowy);

            //when
            List<String> rslt = new InsertCreationClass().insertCreationClass(list, list1);

            //then
            assertEquals("INSERT INTO Baza1 (firstname,lastname) VALUES ('',''),('',''),('',''),('',''),('','');",rslt);
        }
        @Test
        void insertCreationClass_nullstringtables() {
            //given
            Field field = new TextField();
            field.setFieldInfo(new String[]{"int"});

            List<TableMappingClass> list = new ArrayList<>();
            ColumnMappingClass column1 = ColumnMappingClass.builder()
                    .name("firstname")
                    .field(field)
                    .build();
            ColumnMappingClass column2 = ColumnMappingClass.builder()
                    .name("lastname")
                    .field(field)
                    .build();
            TableMappingClass mapping = TableMappingClass.builder()
                    .tableName("Baza1")
                    .tableType(SupportedDatabases.MYSQL)
                    .addColumn(column1)
                    .addColumn(column2)
                    .build();
            list.add(mapping);;
            mapping.setNumberOfGenerations(5);

            String[][] nowy = {{null,null,null,null,null},{null,null,null,null,null}};
            List <String[][]> list1 = new ArrayList<>();
            list1.add(nowy);

            //when
            List<String> rslt = new InsertCreationClass().insertCreationClass(list, list1);

            //then
            assertEquals("INSERT INTO Baza1 (firstname,lastname) VALUES ('null','null'),('null','null'),('null','null'),('null','null'),('null','null');",rslt);
        }
    }
}
