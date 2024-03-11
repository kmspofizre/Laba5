package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveGreaterKeyCommand extends Command{

    public RemoveGreaterKeyCommand(String commandName, String description, boolean hasArguments) {
        super(commandName, description, hasArguments);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        // валидация
        dataBase.removeGreaterKey(id);
    }
}
