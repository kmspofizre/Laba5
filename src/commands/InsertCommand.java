package commands;

import collections.CSVDataBase;
import exceptions.CommandExecutingException;

import java.util.ArrayList;
import java.util.List;

public class InsertCommand extends Command{
    public InsertCommand(String name, String description, boolean hasArguments, boolean isMultiLines) {
        super(name, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws CommandExecutingException{
        List<String []> commandArgs = new ArrayList<>();
        // построчный ввод

        commandArgs.add(args);
        dataBase.insert(commandArgs);
    }
}
