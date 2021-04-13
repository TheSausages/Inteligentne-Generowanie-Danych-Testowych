package DataCreation;

public class RandomVIN {

    //na razie to jest placeholder który generuje cokolwiek na podobieństwo vinu

    public static String[] VIN(int n, double[] rand){

        String[] VinArray = new String[n];

        for (int i=0; i<n; i++){
            VinArray[i] = String.format
                    ("%017d", randBetween.randint(0, 99999, rand[i]));
            System.out.println(VinArray[i]);
        }
        return VinArray;
    }
}
