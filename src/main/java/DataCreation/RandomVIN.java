package DataCreation;

public class RandomVIN implements GenerateInterface, makeDoubleTabelForSeedInterface {

    @Override
    public String[] generate(long seed, int n, String locale){

        String[] VinArray = new String[n];

        double[] rand = makeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);

        for (int i=0; i<n; i++){
            VinArray[i] = String.format
                    ("%017d", randBetween.randint(0, 99999, rand[i]));
        }
        return VinArray;
    }
}
