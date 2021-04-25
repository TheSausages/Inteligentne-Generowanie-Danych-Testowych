package GenerateInformation;

import DataCreation.GenerateInterface;
import TableMapping.Fields.*;
import TableMapping.TableMappingClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JSONFileOperator {
    private final static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(GenerateInterface.class, new GsonGenerationClassAdapter())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Field.class, "fieldType")
                    .registerSubtype(BitField.class, "BitField")
                    .registerSubtype(BlobField.class, "BlobField")
                    .registerSubtype(BooleanField.class, "BooleanField")
                    .registerSubtype(DateTimeField.class, "DateTimeField")
                    .registerSubtype(EnumField.class, "EnumField")
                    .registerSubtype(NumberField.class, "NumberField")
                    .registerSubtype(SetField.class, "SetField")
                    .registerSubtype(TextField.class, "TextField"))
            .create();

    public static void tableJSONToFile(List<TableMappingClass> tables, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(tables, writer);
        } catch (IOException | JsonIOException e) {
            e.printStackTrace();
        }
    }

    public static List<TableMappingClass> fileToTableJSON(String path) {
        try (FileReader reader = new FileReader(path)) {
            Type type = new TypeToken<List<TableMappingClass>>(){}.getType();

            return gson.fromJson(reader, type);
        } catch (IOException | JsonIOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
