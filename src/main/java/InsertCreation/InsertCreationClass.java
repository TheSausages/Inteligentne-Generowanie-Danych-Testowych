package InsertCreation;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import DatabaseConnection.SupportedDatabases;
import TableMapping.ColumnMappingClass;

import TableMapping.TableMappingClass;
import org.apache.commons.lang3.StringUtils;


public class InsertCreationClass {

    public void InsertCreationClass(List<TableMappingClass> mappedTables) {
        var ref = new Object() {
            StringBuilder str1= new StringBuilder("");
            StringBuilder str2= new StringBuilder("");
            StringBuilder str3= new StringBuilder("");
        };

        try {
            File newTextFile = new File("thetextfile.txt");
            FileWriter fw = new FileWriter(newTextFile);
        mappedTables.forEach(tableMappingClass -> {
                    ref.str1.append("INSERT INTO ").append(tableMappingClass.getTableName()).append(" (");
            tableMappingClass.getColumns().forEach(columnMappingClass -> {
                    ref.str2.append(columnMappingClass.getName()).append(",");
            });
            ref.str3.append(") VALUES ('....','....','....','....');");
            try {
                fw.write(ref.str1.toString()+ StringUtils.chop(ref.str2.toString())+ ref.str3.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ref.str1.delete(0, ref.str1.length());
            ref.str2.delete(0, ref.str2.length());
            ref.str3.delete(0, ref.str3.length());
        });
            fw.close();
        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }
}
