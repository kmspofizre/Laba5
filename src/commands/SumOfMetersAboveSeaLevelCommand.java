package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumOfMetersAboveSeaLevelCommand extends Command{
    public SumOfMetersAboveSeaLevelCommand(String commandName, String description,
                                           boolean hasArguments, boolean isMultiLines) {
        super(commandName, description, hasArguments, isMultiLines);
    }

    @Override
    public void execute(String [] args, CSVDataBase dataBase){
        dataBase.sumOfMetersAboveSeaLevel();
    }
}
