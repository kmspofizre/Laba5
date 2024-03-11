package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveCommand extends Command{
    public RemoveCommand(String name, String description, boolean hasArguments) {
        super(name, description, hasArguments);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        // валидация
        String [] argsToGive = Arrays.copyOfRange(args, 1, args.length);
        List<String []> commandArgs = new ArrayList<>();
        commandArgs.add(args);

        dataBase.remove(id);
    }
}
