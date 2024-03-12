package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveCommand extends Command{
    public RemoveCommand(String name, String description, boolean hasArguments, boolean isMultiLines) {
        super(name, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        // валидация
        dataBase.remove(id);
    }
}
