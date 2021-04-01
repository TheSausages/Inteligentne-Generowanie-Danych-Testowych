package DataCreation;

import java.time.LocalDate;

public class RandomPESEL {

    public static String[] PESEL(LocalDate[] birth, double[] rand) {

        String[] ListaP = new String[birth.length];

        for (int i = 0; i < birth.length; i++) {

            int year = birth[i].getYear();
            String y = String.valueOf(year);
            int m = birth[i].getMonthValue();
            if (year > 1999) m += 20;
            int d = birth[i].getDayOfMonth();

            String PESEL = y.substring(2) + String.format("%02d", m) + String.format("%02d", d) + randBetween.randint(10000, 99999, rand[i]);
            ListaP[i] = PESEL;
            //Integer PESEL = Integer.parseInt(P);
            System.out.println("PESEL: " + PESEL);
        }
        return ListaP;
    }
}
