package DataCreation;

import TableMapping.ColumnMappingClass;

import java.util.ArrayList;
import java.util.List;

public class RandomBoolean implements GenerateInterface, MakeDoubleTabelForSeedInterface {

    @Override
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column){

        List<String> booleanArray = new ArrayList<>();

        double[] rand = MakeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);
        for (double number : rand) {
            booleanArray.add(Integer.toString((int) Math.round(number)));
        }

        return booleanArray.toArray(new String[]{});
    }
}
