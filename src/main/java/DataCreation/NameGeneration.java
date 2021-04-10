package DataCreation;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class NameGeneration {

    Faker faker = new Faker(new Locale("pl"),new Random(20));
    String firstName = faker.name().firstName();
}

