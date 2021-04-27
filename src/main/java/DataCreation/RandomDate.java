package DataCreation;

import java.time.LocalDate;
import java.time.Year;

public class RandomDate implements GenerateInterface, makeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale) {

        String[] DateArray = new String[n];

        double[] rand = makeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);

        for (int i=0; i<n; i++) {

            long minDay = LocalDate.of(Year.now().getValue() - 80, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(Year.now().getValue() - 16, 12, 31).toEpochDay();
            long randomDay =  randBetween.randlong(minDay, maxDay, rand[i]);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            DateArray[i] = randomDate.toString();
        }
        return DateArray;


        //java.sql.Date date = java.sql.Date.valueOf(randomDate); //zamiana na java.sql.Date
    }
}
