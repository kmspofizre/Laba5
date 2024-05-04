package components;

import collections.CSVDataBase;

import java.util.TreeMap;

public interface Reversible {
    public Response undo(TreeMap<Long, City> changed, CSVDataBase dataBase);
}
