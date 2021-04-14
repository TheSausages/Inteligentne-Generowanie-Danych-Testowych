package DataCreation;

import java.time.LocalDate;
import java.time.Year;

public class RandomDate {

    public static LocalDate[] Date(int n, double[] rand) {

        LocalDate[] DateArray = new LocalDate[n];
        for (int i=0; i<n; i++) {

            long minDay = LocalDate.of(Year.now().getValue() - 80, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(Year.now().getValue() - 16, 12, 31).toEpochDay();
            long randomDay =  randBetween.randlong(minDay, maxDay, rand[i]);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            DateArray[i] = randomDate;
            System.out.println(randomDate);
        }
        return DateArray;


        //java.sql.Date date = java.sql.Date.valueOf(randomDate); //zamiana na java.sql.Date
    }
}
