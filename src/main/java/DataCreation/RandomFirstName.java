package DataCreation;
import TableMapping.ColumnMappingClass;
import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class RandomFirstName implements GenerateInterface, MakeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column) {

        String[] FirstNameList = new String[n];
        Faker faker = new Faker(new Locale("en"), new Random(seed));

        for (int i=0;i<n;i++)
        {
            FirstNameList[i] = faker.name().firstName();
        }
        return FirstNameList;
    }
}
