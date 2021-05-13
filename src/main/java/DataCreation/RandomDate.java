package DataCreation;

import TableMapping.ColumnMappingClass;

import java.time.LocalDate;
import java.time.Year;

public class RandomDate implements GenerateInterface, MakeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column) {

        String[] DateArray = new String[n];
        double[] rand = MakeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);

        for (int i=0; i<n; i++) {

            long minDay = LocalDate.of(Year.now().getValue() - 80, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(Year.now().getValue() - 16, 12, 31).toEpochDay();
            long randomDay =  RandBetween.randlong(minDay, maxDay, rand[i]);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            DateArray[i] = randomDate.toString();
        }
        return DateArray;

    }
}
