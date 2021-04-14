package InsertCreation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InsertSavingClass {

    public static void InsertSavingClass(String str4){
         try {
            File newTextFile = new File("thetextfile2.txt");
            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str4);
             fw.close();
        } catch (IOException iox) {
           iox.printStackTrace();
        }
    }
}
