package InsertCreation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class InsertSavingClass {
    private File file;
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
    public InsertSavingClass(String path)
    {
        file = new File(path);
    }
}
