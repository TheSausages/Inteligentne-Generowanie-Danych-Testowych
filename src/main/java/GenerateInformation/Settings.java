package GenerateInformation;

import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representation of the Settings file
 */

@Setter
@Getter
@NoArgsConstructor
public class Settings {
    private DatabaseInfo databaseInfo;
    private long seed;
    private String mappingDataPath;
    private String insertPath;
    private String locale;
    private boolean autoFill;

    /**
     * Method used to increment the {@link Settings#seed} field
     */
    public void seedIncrement() {
        seed++;
    }
}
