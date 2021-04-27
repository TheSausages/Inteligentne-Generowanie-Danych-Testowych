package DataCreation;

import java.time.LocalDate;

public class RandomPESEL {

    public static String[] PESEL(LocalDate[] birth, double[] rand, String locale) {

        String[] PesArray = new String[birth.length];

        for (int i = 0; i < birth.length; i++) {

            String y = String.valueOf(birth[i].getYear());

            int month = birth[i].getMonthValue();
            if (birth[i].getYear() > 1999) month += 20;
            String m = String.format("%02d", month);

            int day = birth[i].getDayOfMonth();
            String d = String.format("%02d", day);

            String s_num = String.format("%04d", randBetween.randint(0, 9999, rand[i]));

            int num_11 = checksum(y.substring(2) + m + d + s_num);

            PesArray[i] = y.substring(2) + m + d + s_num + num_11;
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
