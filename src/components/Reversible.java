package components;


import collections.PostgresDataBase;

import java.util.TreeMap;

public interface Reversible {
    public Response undo(TreeMap<Long, City> changed, PostgresDataBase dataBase);
}
