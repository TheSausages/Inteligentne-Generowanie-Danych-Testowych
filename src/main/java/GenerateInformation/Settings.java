package GenerateInformation;

import DatabaseConnection.DatabaseInfo;
import DatabaseConnection.SupportedDatabases;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Settings {
    private DatabaseInfo databaseInfo;
    private long seed;
    private String mappingDataPath;
    private String insertPath;
    private String locale;

    public void seedIncrement() {
        seed++;
    }
}
