package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveCommand extends Command{
    public SaveCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }

    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        dataBase.save();
    }
}
