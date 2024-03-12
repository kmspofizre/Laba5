package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveGreaterKeyCommand extends Command{

    public RemoveGreaterKeyCommand(String commandName, String description,
                                   boolean hasArguments, boolean isMultiLines) {
        super(commandName, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        dataBase.removeGreaterKey(id);
    }
}
