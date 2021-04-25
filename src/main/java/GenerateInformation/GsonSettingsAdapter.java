package GenerateInformation;

import DataCreation.ColumnNameMapping;
import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

public class GsonSettingsAdapter implements JsonDeserializer<Settings> {

    @Override
    public Settings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        Settings settings = new Settings();

        try {
            settings.setSeed(object.get("seed").getAsLong());
        } catch (ClassCastException e) {
            System.out.println("Seed should consist of numbers!");
        }

        DatabaseInfo.DatabaseInfoBuilder builder = DatabaseInfo.builder();

        for (SupportedDatabases databases : SupportedDatabases.values()) {
            if (object.get("databaseType").getAsString().equals(databases.name())) {
                builder.database(databases);
            }
        }

        builder.hostOrServerName(object.get("hostname") instanceof JsonNull ? "" : object.get("hostname").getAsString())
                .portOrInstance(object.get("port") instanceof JsonNull ? "" : object.get("port").getAsString())
                .name(object.get("databaseName").getAsString())
                .username(object.get("username").getAsString())
                .password(object.get("password").getAsString());

        settings.setDatabaseInfo(builder.build());

        settings.setMappingDataPath(object.get("tableMappingFile") instanceof JsonNull ? "TableMapping.txt" : object.get("tableMappingFile").getAsString());
        settings.setInsertPath(object.get("insertFilePath") instanceof JsonNull ? "Inserts.txt" : object.get("insertFilePath").getAsString());

        return settings;
    }
}
