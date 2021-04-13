package DataCreation;
import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class RandomFirstName {

    public static String[] FirstName (int n,long seed) {
        String[] FirstNameList = new String[n];
        Faker faker = new Faker(new Locale("pl"), new Random(seed));

        for (int i=0;i<n;i++)
        {
            FirstNameList[i] = faker.name().firstName();
        }
        return FirstNameList;
    }
}
