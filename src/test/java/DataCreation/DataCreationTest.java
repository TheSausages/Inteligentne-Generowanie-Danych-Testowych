package DataCreation;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DataCreationTest {

    Random rand = new Random();
    long seed = rand.nextInt(10000);
    int n = 50;

    @Nested
    @DisplayName("Salary generation tests")
    class RandomSalaryTest {

        @Test
        void generation_RandomSalaryArray_NoErrors() {

            String[] Salary = (new RandomSalary()).generate(seed, n, "pl-PL");

            assertEquals(Salary.length, n);

            for(int i=0; i<n; i++) {
                assertTrue(Integer.parseInt(Salary[i]) >= 2500);
                assertTrue(Integer.parseInt(Salary[i]) <= 10000);
                assertEquals(Integer.parseInt(Salary[i]) % 100, 0);
            }
        }
    }

    @Nested
    @DisplayName("Date generation tests")
    class RandomDateTest {

        @Test
        void generation_RandomDateArray_NoErrors() {

            String[] Date = (new RandomDate()).generate(seed, n, "pl-PL");

            assertEquals(Date.length, n);

            for(int i=0; i<n; i++) {

                int year = Integer.parseInt(Date[i].substring(0,4));
                int month = Integer.parseInt(Date[i].substring(5,7));
                int day = Integer.parseInt(Date[i].substring(8,10));
                LocalDate d = LocalDate.parse(Date[i]);
                Set<Integer> lm = Set.of(1,3,5,7,8,10,12);
                Set<Integer> sm = Set.of(4,6,9,11);


                assertTrue(Date[i].matches("\\d\\d\\d\\d-\\d\\d-\\d\\d"));
                assertTrue(year <= (Year.now().getValue()-16) & year >= (Year.now().getValue()-80));
                assertTrue(month <= 12 & month >= 1);
                if (lm.contains(month)) assertTrue(day <= 31 & day >= 1);
                else if (sm.contains(month)) assertTrue(day <= 30 & day >= 1);
                else if (month == 2 & d.lengthOfYear() == 365) assertTrue(day <= 28 & day >= 1);
                else if (month == 2 & d.lengthOfYear() == 366) assertTrue(day <= 29 & day >= 1);
            }
        }


    }

    @Nested
    @DisplayName("Boolean generation tests")
    class RandomBooleanTest {

        @Test
        void generation_RandomBooleanArray_NoErrors() {

            String[] Bool = (new RandomBoolean()).generate(seed, n, "pl-PL");

            for(int i=0; i<n; i++) assertTrue(Integer.parseInt(Bool[i]) == 0 | Integer.parseInt(Bool[i]) == 1);
        }
    }

    @Nested
    @DisplayName("PESEL generation tests")
    class RandomPeselTest {

        @Test
        void generation_RandomPeselArray_NoErrors() {

            String[] PESEL = (new RandomPESEL()).generate(seed, n, "pl-PL");
            String[] Date = (new RandomDate()).generate(seed, n, "pl-PL");


            for (int i=0; i<n; i++) {
                assertTrue(PESEL[i].matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d"));
                assertEquals(Date[i].substring(2,4), PESEL[i].substring(0,2));

                int y = Integer.parseInt(Date[i].substring(5,7)) + 20;
                String young = String.valueOf(y);

                if (Integer.parseInt(Date[i].substring(0,4))>1999) assertEquals(young,
                        PESEL[i].substring(2,4));
                else assertEquals(Date[i].substring(5,7), PESEL[i].substring(2,4));

                assertEquals(Date[i].substring(8,10), PESEL[i].substring(4,6));

                int[] c = new int [12];
                for (int j=0; j<11; j++) c[j] = Integer.parseInt(PESEL[i].substring(j, j+1));
                c[11] = c[0] + c[1]*3 + c[2]*7 + c[3]*9 + c[4] + c[5]*3 + c[6]*7 + c[7]*9 + c[8] + c[9]*3 + c[10];

                assertEquals(c[11]%10,0);
            }
        }
    }
}
