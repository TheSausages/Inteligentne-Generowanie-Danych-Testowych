package DataCreation;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.DoubleStream;

public class mainGeneration {

    public static void main(String[] args){

        int k = 5;
        long seed = 1407;
        Random gen = new Random(seed);
        DoubleStream stream = gen.doubles(k);
        double[] array = stream.toArray();

        for(int i=0; i<k; i++) {
            System.out.print(array[i] + "\n");
        }

        //LocalDate[] randomDateList = RandomDate.Date(k, array);
        //RandomPESEL.PESEL(randomDateList, array);
        //RandomSalary.Salary(k, array);
        //RandomVIN.VIN(k, array);
        //String[] name = RandomFirstName.FirstName(k, seed);
        //String[] mail = RandomEmail.Email(name, name);

        /*for (int i=0; i<k; i++){
            System.out.println(name[i]);
            System.out.println(mail[i]);
        }*/
    }
}
