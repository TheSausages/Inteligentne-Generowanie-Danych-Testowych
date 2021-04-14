package DataCreation;

public class RandomSalary implements generateInterface, makeDoubleTabelForSeedInterface {

    @Override
    public String[] generate(long seed, int n) {

        String[] SalaryArray = new String[n];
        int[] SalaryValues = new int[] {
            2500, 2700, 3200, 4500, 8000, 10000};
        double[] rand = makeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);

        for(int i=0; i<n; i++) {
            SalaryArray[i] =
                String.valueOf(SalaryValues[randBetween.randint(0,5, rand[i])]);
        }

        return (SalaryArray);
    }
}
