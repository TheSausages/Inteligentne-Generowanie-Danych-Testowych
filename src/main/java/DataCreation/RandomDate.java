package DataCreation;

import TableMapping.ColumnMappingClass;

import java.time.LocalDate;
import java.time.Year;

public class RandomDate implements GenerateInterface, MakeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column) {

        String[] DateArray = new String[n];
        double[] rand = MakeDoubleTabelForSeedInterface.generateDoubleArray(seed, 2*n); //2n bo inaczej zabraknie doubli przy powtórce; ogarnąć mechanizm
        int i = 0;
        int k = 0;
        boolean unique = true;  //temp

        while (i<n) {

            long minDay = LocalDate.of(Year.now().getValue() - 80, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(Year.now().getValue() - 16, 12, 31).toEpochDay();
            long randomDay =  RandBetween.randlong(minDay, maxDay, rand[i+k]);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            if (unique){
                for (int j=0; j<i; j++) {
                    boolean repeat = (randomDate.toString().equals(DateArray[j]));
                    if(repeat) {
                        k++;
                        break;
                    }
                }
                DateArray[i] = randomDate.toString();
                i++;
            }
            else if (!unique) {
                DateArray[i] = randomDate.toString();
                i++;
            }
        }
        return DateArray;

    }
}
