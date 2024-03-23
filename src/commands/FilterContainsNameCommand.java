package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterContainsNameCommand extends Command{
    public FilterContainsNameCommand(String commandName, String description,
                                     boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        String name = args[0];
        // валидация
        dataBase.filterContainsName(name);
    }
}
