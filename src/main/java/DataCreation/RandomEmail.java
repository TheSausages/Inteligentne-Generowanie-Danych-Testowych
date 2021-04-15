package DataCreation;

public class RandomEmail implements generateInterface, makeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n) {

        String[] EmailArray = new String[n];
        String[] NameArray = (new RandomFirstName()).generate(seed, n);
        String[] SurnameArray = (new RandomLastName()).generate(seed, n);

        for (int i=0; i<n; i++){
            EmailArray[i] = NameArray[i] + "." + SurnameArray[i] + "@company.net";
        }

        return EmailArray;
    }
}
