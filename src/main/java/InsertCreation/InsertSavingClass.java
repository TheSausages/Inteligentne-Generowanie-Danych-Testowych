package InsertCreation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class contains saving-to-file method and creating-file method.
 */
@NoArgsConstructor
@Setter
@Getter
public class InsertSavingClass {
    private File file;

    /**
     * A method that saves to the file.
     * @param str4 is a String that is saved to the file
     */
    public void saveToFile(List<String> str4){
         try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(file));
            for(int k=0;k< str4.size();k++) {
                fw.write(str4.get(k));
                fw.newLine();
            }
             fw.close();
        } catch (IOException iox) {
           iox.printStackTrace();
        }
    }

    /**
     * A method that creates a new file based on the given path
     * @param path is the path to the file
     */
    public InsertSavingClass(String path)
    {
        file = new File(path);
    }
}
