package GenerateInformation;

import DataCreation.GenerateInterface;
import com.google.gson.*;

import java.lang.reflect.Type;

public class GsonGenerationClassAdapter implements JsonSerializer<GenerateInterface>, JsonDeserializer<GenerateInterface> {
    @Override
    public JsonElement serialize(GenerateInterface src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getClass().getName());
    }

    @Override
    public GenerateInterface deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return getClassInstance(json.getAsString());
    }

    public GenerateInterface getClassInstance(String className) {
        GenerateInterface generateInterface = null;

        try {
            generateInterface =  (GenerateInterface) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("There is no generation class of this name!");
        }

        return generateInterface;
    }
}
