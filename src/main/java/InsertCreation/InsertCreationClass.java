package InsertCreation;
import java.io.*;

public class InsertCreationClass {
    public static void InsertCreationClass(String[] args) {

        Data one = new Data();
        one.name = "Piotr";
        one.surname = "Kiszewski";
        one.car = "Volvo";
        one.number = 999;

        Data two= new Data();
        two.name = "Kacper";
        two.surname = "Morelowski";
        two.car = "Suzuki";
        two.number = 778;


        try {
            File newTextFile = new File("C:/thetextfile.txt");
            FileWriter fw = new FileWriter(newTextFile);
            fw.write(one.str);
            fw.write(two.str);
            fw.close();

        } catch (IOException iox) {
            iox.printStackTrace();
        }

        //INSERT INTO table_name (column1, column2, column3, ...)
        //VALUES (value1, value2, value3, ...);
        System.out.printf("insert");
    }
}
