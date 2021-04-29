package DataCreation;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class RandomLastName implements GenerateInterface, MakeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale) {

        String[] LastNameList = new String[n];
        Faker faker = new Faker(new Locale("en"), new Random(seed));

        for (int i=0;i<n;i++)
        {
            LastNameList[i] = faker.name().lastName();
        }
        return LastNameList;
    }
}
