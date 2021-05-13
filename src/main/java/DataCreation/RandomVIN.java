package DataCreation;

import TableMapping.ColumnMappingClass;

public class RandomVIN implements GenerateInterface, MakeDoubleTabelForSeedInterface {

    @Override
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column){

        String[] VinArray = new String[n];

        double[] rand = MakeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);

        for (int i=0; i<n; i++){
            VinArray[i] = String.format
                    ("%017d", RandBetween.randint(0, 99999, rand[i]));
        }
        return VinArray;
    }
}
