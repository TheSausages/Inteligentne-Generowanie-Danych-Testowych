package DataCreation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DataCreationTest {

    Random rand = new Random();
    long seed = rand.nextInt(10000);
    int n = 10;

    @Nested
    @DisplayName("Salary generation tests")
    class RandomSalaryTest {

        @Test
        void generation_RandomSalaryArrayValuesBrackets_NoErrors(){
            String[] Salary = (new RandomSalary()).generate(seed, n, "pl-PL");
            for(int i=0; i<n; i++) {
                assertTrue(Integer.parseInt(Salary[i])>=2500);
                assertTrue(Integer.parseInt(Salary[i])<=10000);
            }
        }

        @Test
        void generation_RandomSalaryArrayLength(){
            String[] Salary = (new RandomSalary()).generate(seed, n, "pl-PL");
            assertEquals(Salary.length, n);
        }
    }

    @Nested
    @DisplayName("Date generation Tests")
    class RandomDateTest {

        //@Test
        //void generation_
    }
}
