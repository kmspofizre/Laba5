package commands;


import collections.PostgresDataBase;
import components.*;

import java.sql.SQLException;
import java.util.*;

public class RemoveGreaterKeyCommand extends Command implements Reversible {

    public RemoveGreaterKeyCommand(String commandName, String description,
                                   boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, PostgresDataBase dataBase, User user) throws SQLException {
        Long id = Long.parseLong(args[0]);
        return dataBase.removeGreaterKey(id, user);
    }
    @Override
    public String[] prepareData(String [] args, Scanner scanner, boolean fromScript) throws NumberFormatException{
        String [] data = new String [1];
        Long id = Long.parseLong(args[0]);
        data[0] = args[0];
        return data;
    }

    @Override
    public String[] prepareScriptData(String [] args, Scanner scanner) throws NumberFormatException{
        String [] data = new String [1];
        Long id = Long.parseLong(args[0]);
        data[0] = args[0];
        return data;
    }

    @Override
    public Request prepareRequest(String [] args, Scanner scanner, boolean fromScript){
        String [] data = prepareData(args, scanner, fromScript);
        return new Request(data);
    }
    @Override
    public Response undo(TreeMap<Long, City> changed, PostgresDataBase dataBase){
        dataBase.insertAllFromCollection(changed);
        return new Response("Удаление элементов коллекции отменено");
    }
}
