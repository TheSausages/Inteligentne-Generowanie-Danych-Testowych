package GenerateInformation;

import DataCreation.ColumnNameMapping;
import DataCreation.MakeDoubleTabelForSeedInterface;
import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.Random;

public class GsonSettingsAdapter implements JsonDeserializer<Settings> {

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
        settings.setMappingDataPath(!object.has("tableMappingFile") || object.get("tableMappingFile") instanceof JsonNull ? "TableMapping.txt" : object.get("tableMappingFile").getAsString());
        settings.setInsertPath(!object.has("insertFilePath") || object.get("insertFilePath") instanceof JsonNull ? "Inserts.txt" : object.get("insertFilePath").getAsString());

        return settings;
    }
}
