package GenerateInformation;

import DataCreation.ColumnNameMapping;
import TableMapping.ColumnMappingClass;
import com.google.gson.*;

import java.lang.reflect.Type;

public class JSONColumnGsonSerializer implements JsonSerializer<ColumnMappingClass> {
    private final Gson gson = new Gson();

    @Override
    public JsonElement serialize(ColumnMappingClass src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject columnObject = new JsonObject();

        columnObject.addProperty("name", src.getName());
        columnObject.addProperty("fieldClass", src.getField().getClass().getSimpleName());
        columnObject.add("field", gson.toJsonTree(src.getField()));
        columnObject.addProperty("nullable", src.isNullable());
        columnObject.addProperty("isAutoIncrement", src.isAutoIncrement());
        columnObject.addProperty("isUnique", src.isUnique());
        columnObject.addProperty("isPrimaryKey", src.isPrimaryKey());
        columnObject.add("isForeignKey", gson.toJsonTree(src.getForeignKey()));
        columnObject.addProperty("ChosenGenerationClass", ColumnNameMapping.getGeneratorName(src));

        return columnObject;
    }
}
