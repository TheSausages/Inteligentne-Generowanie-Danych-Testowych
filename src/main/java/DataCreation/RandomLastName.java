package DataCreation;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class RandomLastName implements generateInterface, makeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n) {

        String[] LastNameList = new String[n];
        Faker faker = new Faker(new Locale("pl"), new Random(seed));

        for (int i=0;i<n;i++)
        {
            LastNameList[i] = faker.name().lastName();
        }
        return LastNameList;
    }
}
