package DataCreation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.round;

public class RandomBoolean implements generateInterface, makeDoubleTabelForSeedInterface {

    @Override
    public String[] generate(long seed, int n){

        List<String> booleanArray = new ArrayList<>();

        double[] rand = makeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);
        for (double number : rand) {
            booleanArray.add(Integer.toString((int) Math.round(number)));
        }

        return booleanArray.toArray(new String[]{});
    }
}
