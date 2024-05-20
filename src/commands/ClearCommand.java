package commands;

import collections.PostgresDataBase;
import components.City;
import components.Request;
import components.Response;
import components.Reversible;

import java.sql.SQLException;
import java.util.*;

public class ClearCommand extends Command implements Reversible {
    public ClearCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, PostgresDataBase dataBase, boolean fromScript) throws SQLException {
        return dataBase.clear();
    }


    @Override
    public Request prepareRequest(String[] args, Scanner scanner, boolean fromScript){
        return new Request(new String[0]);
    }

    @Override
    public Response undo(TreeMap<Long, City> changed, PostgresDataBase dataBase){
        dataBase.insertAllFromCollection(changed);
        return new Response("Очистка коллекции отменена");
    }
}
