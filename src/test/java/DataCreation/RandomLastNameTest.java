package DataCreation;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

class RandomLastNameTest {

    @Test
    public void generatedLastNameMatchRegex() {

        Faker testFaker = new Faker(new Locale("en"),new Random());
        System.out.println(testFaker);
        Pattern LastNamePattern = Pattern.compile("^[a-zA-Z\\s]+");
        Matcher LastNameMatcher = LastNamePattern.matcher(testFaker.name().lastName());
        assertTrue(LastNameMatcher.find());

    }
}
