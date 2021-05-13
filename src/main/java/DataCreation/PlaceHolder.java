package DataCreation;

import TableMapping.ColumnMappingClass;

public class PlaceHolder implements GenerateInterface {
    @Override
    public String[] generate(long seed, int n, String locale, ColumnMappingClass column) {
        return new String[0];
    }
}
