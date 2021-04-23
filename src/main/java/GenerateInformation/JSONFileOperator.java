package GenerateInformation;

import TableMapping.ColumnMappingClass;
import TableMapping.TableMappingClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONFileOperator {
    public static void JSONToFile(List<TableMappingClass> tables, String path) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(ColumnMappingClass.class, new JSONColumnGsonSerializer())
                .create();

        String tablees = gson.toJson(tables);

        System.out.println(tablees);

        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(gson.toJson(tables), writer);
        } catch (IOException | JsonIOException e) {
            e.printStackTrace();
        }
    }
}
