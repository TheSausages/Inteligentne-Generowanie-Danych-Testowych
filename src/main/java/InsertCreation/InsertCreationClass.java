package InsertCreation;
import java.io.*;
import TableMapping.ColumnMappingClass;
import TableMapping.ForeignKeyMapping;
public class InsertCreationClass {
    public static void InsertCreationClass(String[] args) {

    Data one = new Data();
        one.setNamee("Piotr");
        one.setSurname("Kiszowski");
        one.setCar("Volvo");
        one.setNumber(999);

        Data two= new Data();
        two.setNamee("Kacper");
        two.setSurname("Morelowski");
        two.setCar("Suzuki");
        two.setNumber(778);

        ColumnMappingClass jeden = new ColumnMappingClass();
        ColumnMappingClass dwa = new ColumnMappingClass();
        ColumnMappingClass trzy = new ColumnMappingClass();
        ColumnMappingClass cztery = new ColumnMappingClass();
        jeden.setName("name");
        dwa.setName("surname");
        trzy.setName("car");
        cztery.setName("number");
        jeden.setAutoIncrement(false);
        dwa.setAutoIncrement(false);
        trzy.setAutoIncrement(false);
        cztery.setAutoIncrement(true);
        jeden.setDefaultValue("");
        dwa.setDefaultValue("");
        trzy.setDefaultValue("");
        cztery.setDefaultValue("");

        String str1 = "INSERT INTO table_name ("+jeden.getName()+","+dwa.getName()+","+trzy.getName()+","+cztery.getName()+") VALUES ('"+one.getNamee()+"','"+one.getSurname()+"','"+one.getCar()+"','"+one.getNumber()+"');";
        String str2 = "INSERT INTO table_name ("+jeden.getName()+","+dwa.getName()+","+trzy.getName()+","+cztery.getName()+") VALUES ('"+two.getNamee()+"','"+two.getSurname()+"','"+two.getCar()+"','"+two.getNumber()+"');";

        String st = str1 + str2;

        try {
            File newTextFile = new File("C:/thetextfile.txt");
            FileWriter fw = new FileWriter(newTextFile);
            fw.write(st);
            fw.close();

        } catch (IOException iox) {
            iox.printStackTrace();
        }

        //INSERT INTO table_name (column1, column2, column3, ...)
        //VALUES (value1, value2, value3, ...);
        System.out.printf("insert");
    }
}
