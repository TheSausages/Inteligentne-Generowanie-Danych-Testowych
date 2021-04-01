package DataCreation;

import java.time.LocalDate;
import java.time.Year;

public class RandomDate {

    public static LocalDate[] date(int n, double[] rand) {

        int j = 0;
        LocalDate[] randomDateList = new LocalDate[n];
        for (int i = n; i > 0; i--) {

            long minDay = LocalDate.of(Year.now().getValue() - 80, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(Year.now().getValue() - 16, 12, 31).toEpochDay();
            //long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            long randomDay =  randBetween.randlong(minDay, maxDay, rand[j]);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            randomDateList[j] = randomDate;
            j++;
            System.out.println(randomDate);
        }
        return randomDateList;


        //java.sql.Date date = java.sql.Date.valueOf(randomDate); //zamiana na java.sql.Date
    }
}
