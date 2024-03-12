package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClearCommand extends Command{
    public ClearCommand(String name, String description, boolean hasArguments, boolean isMultiLines) {
        super(name, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase){
        dataBase.clear();
    }
}
