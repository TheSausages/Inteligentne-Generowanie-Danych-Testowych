package GenerateInformation;

import DataCreation.GenerateInterface;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Class containing a Gson Serializer and Deserializer for a generation class implementing the {@link GenerateInterface} interface. Example of such class is {@link DataCreation.RandomFirstName}
 */
public class GsonGenerationClassAdapter implements JsonSerializer<GenerateInterface>, JsonDeserializer<GenerateInterface> {

    /**
     * Custom Gson Serializer for a generation class.
     * @param src Selected generation class implementing the {@link GenerateInterface} interface
     * @param typeOfSrc Type of the selected generation class
     * @param context The JsonSerializationContext
     * @return A JSON element that consists of the {@link Class#getSimpleName()} of the {@param src}
     */
    @Override
    public JsonElement serialize(GenerateInterface src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getClass().getSimpleName());
    }

    /**
     * Custom Gson Deserializer for a generation class.
     * @param json A JSON element that consists of the {@link Class#getSimpleName()} of the selected generation class
     * @param typeOfT Type of the JSON element
     * @param context The JsonDeserializationContext
     * @return A instance of the selected generation class that implement the {@link GenerateInterface} interface
     * @throws JsonParseException Thrown when the class cannot be parsed
     */
    @Override
    public GenerateInterface deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return getClassInstance(json.getAsString());
    }

    /**
     * Method that created a instance of the selected generation class
     * @param className Name of the selected generation class
     * @return instance of a class that that implement the {@link GenerateInterface} interface located in the {@link DataCreation}
     */
    private GenerateInterface getClassInstance(String className) {
        try {
            return (GenerateInterface) Class.forName("DataCreation." + className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("There is no generation class of this name!");
            return null;
        }
    }
}
