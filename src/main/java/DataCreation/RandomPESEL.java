package DataCreation;

public class RandomPESEL implements GenerateInterface, makeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale) {

        String[] birth = (new RandomDate()).generate(seed, n, locale);
        double[] rand = makeDoubleTabelForSeedInterface.generateDoubleArray(seed, n);
        String[] PesArray = new String[n];

        for (int i = 0; i < n; i++) {

            String y = birth[i].substring(2,4);

            int month = Integer.parseInt(birth[i].substring(5,7));
            if (Integer.parseInt(birth[i].substring(0,4)) > 1999) month += 20;
            String m = String.format("%02d", month);

            String d = birth[i].substring(8,10);

            String s_num = String.format("%04d", randBetween.randint(0, 9999, rand[i]));

            int num_11 = checksum(y + m + d + s_num);

            PesArray[i] = y + m + d + s_num + num_11;
        }
        return PesArray;
    }

    private static int checksum(String first_10){

        int[] tab = new int[10];
        int sum;

        for(int i=0; i<10; i++) tab[i] = Integer.parseInt(first_10.substring(i, i+1));
        int s = 10 - ((tab[0] + tab[1]*3 + tab[2]*7 + tab[3]*9 + tab[4] + tab[5]*3 + tab[6]*7
                + tab[7]*9 + tab[8] + tab[9]*3)%10);
        if (s==10) sum = 0;
        else sum = s;

        return sum;
    }
}
