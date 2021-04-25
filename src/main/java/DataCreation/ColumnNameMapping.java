package DataCreation;

import TableMapping.ColumnMappingClass;

import java.lang.reflect.InvocationTargetException;

public enum ColumnNameMapping {
    RandomFirstName("(([Ff]irst|([Ss]econd))[nN]ames?)|(I|i)mię"),
    RandomDate("(([Bb]irth)?.?(D|d)ate)|([Bb]irth)"),
    RandomSalary("(S|s)alar(y|ies)"),
    Id(".*_?(id|ID)"),
    RandomLastName("((L|l)ast(N|n)ame)|((S|s)urname)|((N|n)azwisko)"),
    RandomPESEL("(PESEL)|((P|p)esel)"),
    RandomEmail("(E|e)-?mail.?((A|a)ddress)?"),
    RandomVIN("((VIN)|(([Vv])in))"),
    RandomBoolean("isAlive");


    private final String regex;

    ColumnNameMapping(String regex) {
        this.regex = regex;
    }

    private String getRegex() {
        return this.regex;
    }


    private generateInterface getGenerationClass() {
        generateInterface generationClass = null;

        try {
            generationClass = (generateInterface) Class.forName("DataCreation." + this.name()).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Could not find Generation class!");
        }

        return generationClass;
    }

    public static generateInterface getGenerator(ColumnMappingClass column) {
        for (ColumnNameMapping category : ColumnNameMapping.values()) {
            if (column.getName().matches(category.getRegex())) {
                return category.getGenerationClass();
            }
        }

        throw new RuntimeException("There is no mapping for column name:" + column.getName());
    }
}
