package DataCreation;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

class RandomFirstNameTest{

    @Test
        public void generatedFirstNameMatchRegex() {

        Faker testFaker = new Faker(new Locale("en"),new Random());
        System.out.println(testFaker);
        Pattern FirstNamePattern = Pattern.compile("^[a-zA-Z\\s]+");
        Matcher FirstNameMatcher = FirstNamePattern.matcher(testFaker.name().firstName());
        assertTrue(FirstNameMatcher.find());

    }
}

