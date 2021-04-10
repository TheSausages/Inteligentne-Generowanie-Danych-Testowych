package DatabaseConnection;

public enum DataSeizingSQLQueries {
    //MySQL
    TableInformationMySQL("SHOW CREATE TABLE %s"),

    //Oracle
    TableNamesOracle("select object_name from sys.all_objects where object_type = 'TABLE' and owner != 'SYS' and created > (Select created from V$DATABASE)"),
    GetTableInformationOracle("Select TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE, DATA_DEFAULT, IDENTITY_COLUMN from ALL_TAB_COLUMNS\n" +
            "where table_name = '%s'"),
    GetTableConstraintsInformationOracle("Select a.table_name child_table, c.constraint_type, a.column_name child_column, a.constraint_name, a.table_name, a.table_name" +
            "  FROM all_cons_columns a\n" +
            "  JOIN all_constraints c ON a.owner = c.owner AND a.constraint_name = c.constraint_name\n" +
            "WHERE a.table_name = '%s' AND c.constraint_type NOT IN 'R'\n" +
            "union\n" +
            "Select a.table_name child_table, c.constraint_type, a.column_name child_column, a.constraint_name, b.table_name parent_table, b.column_name parent_column" +
            "  FROM all_cons_columns a\n" +
            "  JOIN all_constraints c ON a.owner = c.owner AND a.constraint_name = c.constraint_name\n" +
            "  join all_cons_columns b on c.owner = b.owner and c.r_constraint_name = b.constraint_name\n" +
            "WHERE a.table_name = '%<s'"),


    //SQL Server
    GetTableNamesSQLServer("SELECT name FROM sys.objects WHERE type = 'U' and name Not In ('dtproperties','sysdiagrams')"),
    GetTableInformationSQLServer("Select table_catalog, table_schema, table_name, column_name, column_default, scol.IS_NULLABLE, data_type, precision, scale, is_identity\n" +
            "from INFORMATION_SCHEMA.COLUMNS scol\n" +
            "inner join sys.columns col\n" +
            "on col.name = scol.column_name\n" +
            "where scol.Table_name = '%s'"),
    GetTableConstraintsSQLServer("select table_name,\n" +
            "    object_type, \n" +
            "    constraint_type,\n" +
            "    constraint_name,\n" +
            "    details\n" +
            "from (\n" +
            "    select t.[name] as table_name, \n" +
            "        case when t.[type] = 'U' then 'Table'\n" +
            "            when t.[type] = 'V' then 'View'\n" +
            "            end as [object_type],\n" +
            "        case when c.[type] = 'PK' then 'Primary key'\n" +
            "            when c.[type] = 'UQ' then 'Unique constraint'\n" +
            "            when i.[type] = 1 then 'Unique clustered index'\n" +
            "            when i.type = 2 then 'Unique index'\n" +
            "            end as constraint_type, \n" +
            "        isnull(c.[name], i.[name]) as constraint_name,\n" +
            "        substring(column_names, 1, len(column_names)-1) as [details]\n" +
            "    from sys.objects t\n" +
            "        left outer join sys.indexes i\n" +
            "            on t.object_id = i.object_id\n" +
            "        left outer join sys.key_constraints c\n" +
            "            on i.object_id = c.parent_object_id \n" +
            "            and i.index_id = c.unique_index_id\n" +
            "       cross apply (select col.[name] + ', '\n" +
            "                        from sys.index_columns ic\n" +
            "                            inner join sys.columns col\n" +
            "                                on ic.object_id = col.object_id\n" +
            "                                and ic.column_id = col.column_id\n" +
            "                        where ic.object_id = t.object_id\n" +
            "                            and ic.index_id = i.index_id\n" +
            "                                order by col.column_id\n" +
            "                                for xml path ('') ) D (column_names)\n" +
            "    where is_unique = 1\n" +
            "    and t.is_ms_shipped <> 1\n" +
            "\tand t.[name] = '%s'\n" +
            "    union all \n" +
            "    select fk_tab.name as foreign_table,\n" +
            "        'Table',\n" +
            "        'Foreign key',\n" +
            "\t\t(OBJECT_NAME (f.referenced_object_id) + ',' + COL_NAME(fk_cols.referenced_object_id, fk_cols.referenced_column_id)) as referenced,\n" +
            "\t\tCOL_NAME(fk_cols.parent_object_id,fk_cols.parent_column_id) as referencing\n" +
            "    from sys.foreign_keys fk\n" +
            "        inner join sys.tables fk_tab\n" +
            "            on fk_tab.object_id = fk.parent_object_id\n" +
            "        inner join sys.tables pk_tab\n" +
            "            on pk_tab.object_id = fk.referenced_object_id\n" +
            "        inner join sys.foreign_key_columns fk_cols\n" +
            "            on fk_cols.constraint_object_id = fk.object_id\n" +
            "\t\tinner join sys.foreign_keys f\n" +
            "\t\t\tON f.object_id = fk_cols.constraint_object_id\n" +
            "\twhere fk_tab.name = '%<s'\n" +
            "    union all\n" +
            "    select t.[name],\n" +
            "        'Table',\n" +
            "        'Check constraint',\n" +
            "        con.[name] as constraint_name,\n" +
            "        con.[definition]\n" +
            "    from sys.check_constraints con\n" +
            "        left outer join sys.objects t\n" +
            "            on con.parent_object_id = t.object_id\n" +
            "        left outer join sys.all_columns col\n" +
            "            on con.parent_column_id = col.column_id\n" +
            "            and con.parent_object_id = col.object_id\n" +
            "\t and t.[name] = '%<s'\n" +
            "    union all\n" +
            "    select t.[name],\n" +
            "        'Table',\n" +
            "        'Default constraint',\n" +
            "        con.[name],\n" +
            "        col.[name] + ' = ' + con.[definition]\n" +
            "    from sys.default_constraints con\n" +
            "        left outer join sys.objects t\n" +
            "            on con.parent_object_id = t.object_id\n" +
            "        left outer join sys.all_columns col\n" +
            "            on con.parent_column_id = col.column_id\n" +
            "            and con.parent_object_id = col.object_id\n" +
            "\t Where t.[name] = '%<s') a");

    public final String query;

    DataSeizingSQLQueries(String query) {
        this.query = query;
    }
}
