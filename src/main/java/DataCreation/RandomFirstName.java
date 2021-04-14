package DataCreation;
import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class RandomFirstName implements generateInterface, makeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n) {

        String[] FirstNameList = new String[n];
        Faker faker = new Faker(new Locale("en"), new Random(seed));

        for (int i=0;i<n;i++)
        {
            FirstNameList[i] = faker.name().firstName();
        }
        return FirstNameList;
    }
}
