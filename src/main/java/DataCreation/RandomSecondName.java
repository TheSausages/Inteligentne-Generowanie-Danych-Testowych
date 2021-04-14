package DataCreation;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class RandomSecondName {

    public static String[] SecondName (int n,long seed) {
        String[] SecondNameList = new String[n];
        Faker faker = new Faker(new Locale("pl"), new Random(seed));

        for (int i=0;i<n;i++)
        {
            SecondNameList[i] = faker.name().firstName();
        }
        return SecondNameList;
    }
}
