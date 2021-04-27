package DataCreation;

public class RandomEmail implements GenerateInterface, makeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale) {

        String[] EmailArray = new String[n];
        String[] NameArray = (new RandomFirstName()).generate(seed, n, locale);
        String[] SurnameArray = (new RandomLastName()).generate(seed, n, locale);

        for (int i=0; i<n; i++){
            EmailArray[i] = NameArray[i] + "." + SurnameArray[i] + "@company.net";
        }

        return EmailArray;
    }
}
