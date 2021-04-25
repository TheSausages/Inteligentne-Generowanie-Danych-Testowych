package DataCreation;

import TableMapping.ColumnMappingClass;

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

    public static GenerateInterface getGenerator(ColumnMappingClass column) {
        for (ColumnNameMapping category : ColumnNameMapping.values()) {
            if (column.getName().matches(category.getRegex())) {
                return category.getGenerationClassInstance();
            }
        }

        throw new RuntimeException("There is no mapping for column name:" + column.getName());
    }

    private GenerateInterface getGenerationClassInstance() {
        GenerateInterface generationClass = null;

        try {
            generationClass = (GenerateInterface) Class.forName("DataCreation." + this.name()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Could not find mapping Type!");
        }

        return generationClass;
    }
}
