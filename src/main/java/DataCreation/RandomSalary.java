package DataCreation;

public class RandomSalary implements GenerateInterface, makeDoubleTabelForSeedInterface {

    @Override
    public String[] generate(long seed, int n, String locale) {

        String[] SalaryArray = new String[n];
        //int[] SalaryValues = {2500, 2700, 3200, 4500, 8000, 10000};
        double[] rand = makeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);

        for(int i=0; i<n; i++) {
            SalaryArray[i] =
                String.valueOf(100 * randBetween.randint(25,100, rand[i]));
        }

        return (SalaryArray);
    }
}
