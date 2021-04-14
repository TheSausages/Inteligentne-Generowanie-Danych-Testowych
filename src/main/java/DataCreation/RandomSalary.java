package DataCreation;

public class RandomSalary {

    public static String[] Salary(int n, double[] rand) {

    String[] SalaryArray = new String[n];
    int[] SalaryValues = new int[] {
            2500, 2700, 3200, 4500, 8000, 10000};

    for(int i=0; i<n; i++) {
        SalaryArray[i] =
                String.valueOf(SalaryValues[randBetween.randint(0,5, rand[i])]);
        System.out.println(SalaryArray[i]);
    }

    return (SalaryArray);
    }
}
