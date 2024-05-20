package commands;

import collections.PostgresDataBase;
import components.*;

import java.sql.SQLException;
import java.util.*;

public class ClearCommand extends Command implements Reversible {
    public ClearCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }


    public Response execute(String [] args, PostgresDataBase dataBase, User user) throws SQLException {
        return dataBase.clear(user);
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
