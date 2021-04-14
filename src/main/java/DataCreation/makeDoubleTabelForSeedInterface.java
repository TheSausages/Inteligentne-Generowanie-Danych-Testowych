package DataCreation;

import java.util.Random;
import java.util.stream.DoubleStream;

public interface makeDoubleTabelForSeedInterface {
    public static double[] generateDoubleArray(long seed, int k) {
        Random gen = new Random(seed);
        DoubleStream stream = gen.doubles(k);
        return stream.toArray();
    }
}
