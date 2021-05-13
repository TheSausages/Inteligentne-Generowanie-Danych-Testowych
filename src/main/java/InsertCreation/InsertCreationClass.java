package InsertCreation;
import java.util.ArrayList;
import java.util.List;
import TableMapping.TableMappingClass;
import org.apache.commons.lang3.StringUtils;


/**
 * A class that contains a method that generates inserts in a form of a list of strings
 */
public class InsertCreationClass {

    /**
     * The method that generates inserts in a form of a list of strings
     * @param mappedTables contains information from {@link TableMappingClass} about table and its columns
     * @param list1 contains information about data which are meant to be put in columns
     * @return List of strings which contains inserts
     */
    public List<String> insertCreationClass(List<TableMappingClass> mappedTables, List<String[][]> list1) {
        var ref = new Object() {
            StringBuilder str1= new StringBuilder("");
            StringBuilder str2= new StringBuilder("");
            StringBuilder str3= new StringBuilder("");
            StringBuilder str4= new StringBuilder("");
            StringBuilder str5= new StringBuilder("");
            StringBuilder strx= new StringBuilder("");
            List<String> inserts = new ArrayList<>();
        };
        for (int i=0; i<mappedTables.size(); i++) {
              TableMappingClass tableMappingClass = mappedTables.get(i);
              String[][] list =list1.get(i);

                    ref.str1.append("INSERT INTO ").append(tableMappingClass.getTableName()).append(" (");
            tableMappingClass.getColumns().stream().filter(column -> !column.isAutoIncrement()).forEach(columnMappingClass -> {
                    ref.str2.append(columnMappingClass.getName()).append(",");
            });
            ref.str3.append(") VALUES ");

            for (int k = 0; k < tableMappingClass.getNumberOfGenerations(); k++) {
                ref.str3.append("(");
                for (String[] lista : list) {
                    ref.str3.append("'");
                    ref.str3.append(lista[k]);
                    ref.str3.append("'");
                    ref.str3.append(",");
                }
                ref.str4.append(StringUtils.chop(ref.str3.toString()));
                ref.str3.delete(0, ref.str3.length());
                ref.str4.append("),");
            }

            ref.str5.append(StringUtils.chop(ref.str4.toString()));
            ref.str5.append(";");
            ref.strx.append(ref.str1.toString()).append(StringUtils.chop(ref.str2.toString())).append(ref.str5.toString());
            ref.inserts.add(ref.strx.toString());
            ref.str1.delete(0, ref.str1.length());
            ref.str2.delete(0, ref.str2.length());
            ref.str3.delete(0, ref.str3.length());
            ref.str4.delete(0, ref.str4.length());
            ref.str5.delete(0, ref.str5.length());
            ref.strx.delete(0, ref.strx.length());
        }

        return ref.inserts;
    }

}
