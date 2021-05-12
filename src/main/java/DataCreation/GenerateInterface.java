package DataCreation;

import TableMapping.ColumnMappingClass;

public interface GenerateInterface {
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column);
}
