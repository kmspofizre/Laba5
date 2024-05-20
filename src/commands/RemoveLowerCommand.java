package commands;


import collections.PostgresDataBase;
import components.City;
import components.Request;
import components.Response;
import components.Reversible;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeMap;

public class RemoveLowerCommand extends Command implements Reversible {

    public RemoveLowerCommand(String commandName, String description,
                              boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, PostgresDataBase dataBase, boolean fromScript) throws NumberFormatException, SQLException {
        Long id = Long.parseLong(args[0]);
        return dataBase.removeLower(id, fromScript);
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
