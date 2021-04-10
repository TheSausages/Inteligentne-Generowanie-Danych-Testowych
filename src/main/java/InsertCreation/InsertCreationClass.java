package InsertCreation;
import java.util.List;

import TableMapping.TableMappingClass;
import org.apache.commons.lang3.StringUtils;


public class InsertCreationClass {

    public String InsertCreationClass(List<TableMappingClass> mappedTables) {
        var ref = new Object() {
            StringBuilder str1= new StringBuilder("");
            StringBuilder str2= new StringBuilder("");
            StringBuilder str3= new StringBuilder("");
            StringBuilder str4= new StringBuilder("");
        };

        mappedTables.forEach(tableMappingClass -> {
                    ref.str1.append("INSERT INTO ").append(tableMappingClass.getTableName()).append(" (");
            tableMappingClass.getColumns().forEach(columnMappingClass -> {
                    ref.str2.append(columnMappingClass.getName()).append(",");
            });
            ref.str3.append(") VALUES ('....','....','....','....');");
            ref.str4.append(ref.str1.toString()+ StringUtils.chop(ref.str2.toString())+ ref.str3.toString());
            ref.str1.delete(0, ref.str1.length());
            ref.str2.delete(0, ref.str2.length());
            ref.str3.delete(0, ref.str3.length());
        });
    return ref.str4.toString();
    }

}
