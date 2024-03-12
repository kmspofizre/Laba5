package commands;

import collections.CSVDataBase;
import exceptions.CommandExecutingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateCommand extends Command{

    public UpdateCommand(String name, String description, boolean hasArguments, boolean isMultiLines) {
        super(name, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException, CommandExecutingException{
        Long id = Long.parseLong(args[0]);
        // валидация
        // построчный ввод
        String [] argsToGive = Arrays.copyOfRange(args, 1, args.length);
        if (argsToGive.length != 10){
            throw new CommandExecutingException("Недостаточно данных");
        }
        List<String []> commandArgs = new ArrayList<>();
        commandArgs.add(args);

        dataBase.update(id, commandArgs);
    }
}
