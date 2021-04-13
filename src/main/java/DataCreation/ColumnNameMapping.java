package DataCreation;

public enum ColumnNameMapping {
    RandomFirstName("((F|f)irst|(S|s)econd)(n|N)ames?");

    private final String regex;

    ColumnNameMapping(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }

    private Class<?> getGenerationClass() {
        Class<?> generationClass = null;

        try {
            generationClass = Class.forName("DataCreation." + this.name());
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find Generation class!");
        }

        return generationClass;
    }

    public static Class<?> getGenerator(String columnName) {
        for (ColumnNameMapping category : ColumnNameMapping.values()) {
            if (columnName.matches(category.getRegex())) {
                return category.getGenerationClass();
            }
        }

        throw new IllegalArgumentException("No SUch column Mapping");
    }
}
