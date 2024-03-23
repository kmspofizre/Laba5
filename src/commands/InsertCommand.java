package commands;

import collections.CSVDataBase;
import exceptions.CommandExecutingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertCommand extends Command{
    public InsertCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript) throws CommandExecutingException,
            NumberFormatException{
        Long id = Long.parseLong(args[0]);
        // валидация
        // построчный ввод
        String [] argsToGive = Arrays.copyOfRange(args, 1, args.length);
        if (argsToGive.length != 10){
            throw new CommandExecutingException("Недостаточно данных");
        }
        List<String []> commandArgs = new ArrayList<>();
        commandArgs.add(argsToGive);
        dataBase.insert(id, commandArgs, fromScript);
    }
}
