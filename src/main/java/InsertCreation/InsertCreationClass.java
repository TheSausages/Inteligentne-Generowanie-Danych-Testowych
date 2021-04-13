package InsertCreation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import TableMapping.TableMappingClass;
import org.apache.commons.lang3.StringUtils;
import DataCreation.RandomPESEL;



public class InsertCreationClass {

    public String InsertCreationClass(List<TableMappingClass> mappedTables,String[] test,String[] test2) {
        var ref = new Object() {
            StringBuilder str1= new StringBuilder("");
            StringBuilder str2= new StringBuilder("");
            StringBuilder str3= new StringBuilder("");
            StringBuilder str4= new StringBuilder("");
            StringBuilder str5= new StringBuilder("");
            StringBuilder strx= new StringBuilder("");
        };

        mappedTables.forEach(tableMappingClass -> {
                    ref.str1.append("INSERT INTO ").append(tableMappingClass.getTableName()).append(" (");
            tableMappingClass.getColumns().forEach(columnMappingClass -> {
                    ref.str2.append(columnMappingClass.getName()).append(",");
            });
            ref.str3.append(") VALUES ");
            String[][] list1 = {test, test2};
            for (int i = 0; i < test.length; i++) {
                ref.str3.append("(");
                for (String[] lista : list1) {
                    ref.str3.append(lista[i]);
                    ref.str3.append(",");
                }
                ref.str4.append(StringUtils.chop(ref.str3.toString()));
                ref.str3.delete(0, ref.str3.length());
                ref.str4.append("),");
            }
            ref.str5.append(StringUtils.chop(ref.str4.toString()));
            ref.str5.append(";");
            ref.strx.append(ref.str1.toString()+ StringUtils.chop(ref.str2.toString())+ ref.str5.toString());
            ref.str1.delete(0, ref.str1.length());
            ref.str2.delete(0, ref.str2.length());
            ref.str3.delete(0, ref.str3.length());
            ref.str4.delete(0, ref.str3.length());
            ref.str5.delete(0, ref.str3.length());
        });
    return ref.strx.toString();
    }

}
