package commands;

import collections.CSVDataBase;
import exceptions.CommandExecutingException;

import java.util.ArrayList;
import java.util.List;

public class InsertCommand extends Command{
    public InsertCommand(String name, String description, boolean hasArguments) {
        super(name, description, hasArguments);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws CommandExecutingException{
        List<String []> commandArgs = new ArrayList<>();
        if (args.length != 10){
            throw new CommandExecutingException("Недостаточно данных");
        }
        commandArgs.add(args);
        dataBase.insert(commandArgs);
    }
}
