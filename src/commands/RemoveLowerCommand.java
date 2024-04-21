package commands;

import collections.CSVDataBase;
import components.Request;
import components.Response;

import java.util.Scanner;

public class RemoveLowerCommand extends Command{

    public RemoveLowerCommand(String commandName, String description,
                              boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, CSVDataBase dataBase, boolean fromScript) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        return dataBase.removeLower(id, fromScript);
    }
    @Override
    public String[] prepareData(String [] args, Scanner scanner) throws NumberFormatException{
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
    public Request prepareRequest(String [] args, Scanner scanner){
        String [] data = prepareData(args, scanner);
        return new Request(data);
    }
}
