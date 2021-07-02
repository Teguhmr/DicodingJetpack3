package com.teguh.dicodingjetpack3.utils;

import androidx.sqlite.db.SimpleSQLiteQuery;

public class SortUtils {
    public static final String NEWEST = "Great Rated";
    public static final String OLDEST = "Worst Rated";
    public static final String RANDOM = "Random";
    public static SimpleSQLiteQuery getSortedQuery(String filter) {
        StringBuilder simpleQuery = new StringBuilder().append("SELECT * FROM itemEntities ");
        switch (filter) {
            case NEWEST:
                simpleQuery.append("ORDER BY rating DESC");
                break;
            case OLDEST:
                simpleQuery.append("ORDER BY rating ASC");
                break;
            case RANDOM:
                simpleQuery.append("ORDER BY RANDOM()");
                break;
        }
        return new SimpleSQLiteQuery(simpleQuery.toString());
    }
}
