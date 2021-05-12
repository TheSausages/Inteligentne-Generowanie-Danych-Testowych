package GenerateInformation;

import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Random;

/**
 * Class containing the a Gson Deserializer for the {@link Settings} class from a .json file.
 */
public class GsonSettingsAdapter implements JsonDeserializer<Settings> {

    /**
     * Method returning a complete {@link Settings} class created from a .json file. Necessary information are:
     *      - a valid database type. List of supported types located in {@link SupportedDatabases}
     *      - a valid database name
     *      - Account information (username, password) used to connect to the database
     * Optional informations are:
     *      - seed of type {@link Long} used in data generation (default: random value created using {@link Random})
     *      - hostname on which the database is running (default: depends on the database)
     *      - port on which the database is running (default: depends on the database)
     *      - locale for which data should be generated (default: is pl-PL)
     *      - tableMappingFile which will point to file where the mapped information will be placed (default: TableMapping.json file in the folder the program is located)
     *      - insertFilePath which point to a file where insert are created - valid if autoFill is true (default: Inserts.txt file in the folder the program is located)
     *      - autoFill - if set to true will connect and execute the Inserts directly to the database (default: false)
     * @param json A JSON element containing information from the file
     * @param typeOfT type of file
     * @param context The JsonDeserializationContext
     * @return A complete {@link Settings} class containing all the information from the file
     * @throws JsonParseException Thrown when the file couldn't be parsed
     */
    @Override
    public Settings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();


        if (!object.has("databaseType")) {
            throw new JsonParseException("No Database Type Was Selected!");
        }

        if (!object.has("databaseName")) {
            throw new JsonParseException("No Database Name Was Selected!");
        }

        if (!object.has("username")) {
            throw new JsonParseException("No Username Was Selected!");
        }

        if (!object.has("password")) {
            throw new JsonParseException("No Password Was Selected!");
        }

        Settings settings = new Settings();

        DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder();

        for (SupportedDatabases databases : SupportedDatabases.values()) {
            if (object.get("databaseType").getAsString().equals(databases.name())) {
                builder.database(databases);
            }
        }

        try {
            settings.setSeed(!object.has("seed") || object.get("seed") instanceof JsonNull ? new Random().nextLong() : object.get("seed").getAsLong());
        } catch (ClassCastException | UnsupportedOperationException | NumberFormatException e) {
            throw new JsonParseException("Seed should consist of numbers!");
        }

        builder.hostOrServerName(!object.has("hostname") || object.get("hostname") instanceof JsonNull ? "" : object.get("hostname").getAsString())
                .portOrInstance(!object.has("port") || object.get("port") instanceof JsonNull ? "" : object.get("port").getAsString())
                .name(object.get("databaseName").getAsString())
                .username(object.get("username").getAsString())
                .password(object.get("password").getAsString());

        settings.setDatabaseInfo(builder.build());

        settings.setLocale(!object.has("locale") || object.get("locale") instanceof JsonNull ? "pl-PL" : object.get("locale").getAsString());
        settings.setMappingDataPath(!object.has("tableMappingFile") || object.get("tableMappingFile") instanceof JsonNull ? "TableMapping.json" : object.get("tableMappingFile").getAsString());
        settings.setInsertPath(!object.has("insertFilePath") || object.get("insertFilePath") instanceof JsonNull ? "Inserts.txt" : object.get("insertFilePath").getAsString());
        settings.setAutoFill(object.has("autoFill") && !(object.get("autoFill") instanceof JsonNull) && object.get("autoFill").getAsBoolean());

        return settings;
    }
}
