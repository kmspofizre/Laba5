package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveGreaterKeyCommand extends Command{

    public RemoveGreaterKeyCommand(String commandName, String description,
                                   boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        dataBase.removeGreaterKey(id);
    }
}
