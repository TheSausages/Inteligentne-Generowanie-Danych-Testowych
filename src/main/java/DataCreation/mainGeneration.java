package DataCreation;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.DoubleStream;

public class mainGeneration {

    public static void main(String[] args){

        int k = 5;
        long seed = 54;
        Random gen = new Random(seed);
        DoubleStream stream = gen.doubles(k);
        double[] array = stream.toArray();

        for(int i=0; i<k; i++) {
            System.out.print(array[i] + "\n");
        }

        RandomFirstName.FirstName(k,seed);
        RandomSecondName.SecondName(k,seed);
        LocalDate[] randomDateList = RandomDate.date(5, array);
        RandomPESEL.PESEL(randomDateList, array);
    }
}
