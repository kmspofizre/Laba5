package commands;

import collections.CSVDataBase;
import components.Request;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RemoveCommand extends Command{
    public RemoveCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        Long id = Long.parseLong(args[0]);
        dataBase.remove(id, fromScript);
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
        return new Request(data[0]);
    }
}
