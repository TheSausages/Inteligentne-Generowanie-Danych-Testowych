package DataCreation;

import TableMapping.ColumnMappingClass;
import TableMapping.Fields.*;

public enum ColumnNameMapping {
    RandomFirstName("(([Ff]irst|([Ss]econd))[nN]ames?)|(I|i)mię"),
    RandomSecondName("([Ll]ast)(n|N)ames?"),
    RandomIsAlive("[Ii]s[Aa]live"),
    RandomDate("((B|b)irth)?.?(D|d)ate"),
    RandomSalary("(S|s)alar(y|ies)"),
    Id(".*_?(id|ID)"),
    RandomLastName("((L|l)ast(N|n)ame)|((S|s)urname)|((N|n)azwisko)"),
    RandomPESEL("(PESEL)|((P|p)esel)"),
    RandomEmail("(E|e)-?mail.?((A|a)ddress)?"),
    RandomVIN("(VIN)|((V|v)in");


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
