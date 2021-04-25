package InsertCreation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
@NoArgsConstructor
@Setter
@Getter
public class InsertSavingClass {
    private File file;
    public void saveToFile(String str4){
         try {
            FileWriter fw = new FileWriter(this.file);
            fw.write(str4);
             fw.close();
        } catch (IOException iox) {
           iox.printStackTrace();
        }
    }
    public InsertSavingClass(String path)
    {
        file = new File(path);
    }
}
