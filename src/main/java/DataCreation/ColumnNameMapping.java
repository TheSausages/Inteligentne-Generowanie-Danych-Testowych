package DataCreation;

import TableMapping.ColumnMappingClass;
import TableMapping.Fields.*;

public enum ColumnNameMapping {
    RandomFirstName("([Ff]irst|([Ss]econd))[nN]ames?"),
    RandomSecondName("([Ll]ast)(n|N)ames?"),
    RandomPESEL("[Ee]mails?"),
    RandomIsAlive("[Ii]s[Aa]live"),
    RandomDate("Birth"),
    RandomSalary("(S|s)alar(y|ies)"),
    Id(".*_?(id|ID)");

    private final String regex;

    ColumnNameMapping(String regex) {
        this.regex = regex;
    }

    private String getRegex() {
        return this.regex;
    }


    private Class<?> getGenerationClass() {
        Class<?> generationClass = null;

        try {
            System.out.println(this.name());
            generationClass = Class.forName("DataCreation." + this.name());
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find Generation class!");
        }

        return generationClass;
    }

    public static Class<?> getGenerator(ColumnMappingClass column) {
        for (ColumnNameMapping category : ColumnNameMapping.values()) {
            if (column.getName().matches(category.getRegex())) {
                return category.getGenerationClass();
            }
        }

        throw new RuntimeException("There is no mapping for column name:" + column.getName());
    }
}
