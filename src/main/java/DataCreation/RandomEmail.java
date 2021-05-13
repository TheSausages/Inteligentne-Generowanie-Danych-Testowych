package DataCreation;

import TableMapping.ColumnMappingClass;

public class RandomEmail implements GenerateInterface, MakeDoubleTabelForSeedInterface {
    @Override
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column) {

        String[] EmailArray = new String[n];
        String[] NameArray = (new RandomFirstName()).generate(seed, n, locale, column);
        String[] SurnameArray = (new RandomLastName()).generate(seed, n, locale, column);

        for (int i=0; i<n; i++){
            EmailArray[i] = NameArray[i] + "." + SurnameArray[i] + "@company.net";
        }

        return EmailArray;
    }
}
