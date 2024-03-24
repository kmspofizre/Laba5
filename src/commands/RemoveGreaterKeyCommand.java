package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RemoveGreaterKeyCommand extends Command{

    public RemoveGreaterKeyCommand(String commandName, String description,
                                   boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        Long id = Long.parseLong(args[0]);
        dataBase.removeGreaterKey(id);
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
}
