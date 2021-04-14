package DataCreation;

public class RandomEmail {

    public static String[] Email (String[] FirstName, String[] LastName){

        String[] EmailArray = new String[FirstName.length];

        for (int i=0; i<FirstName.length; i++){
            EmailArray[i] = FirstName[i] + "." + LastName[i] + "@company.net";
        }

        return EmailArray;
    }
}
