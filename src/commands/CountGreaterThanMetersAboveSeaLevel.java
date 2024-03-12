package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountGreaterThanMetersAboveSeaLevel extends Command{
    public CountGreaterThanMetersAboveSeaLevel(String commandName, String description,
                                               boolean hasArguments, boolean isMultiLines) {
        super(commandName, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException{
        Double metersAboveSeaLevel = Double.parseDouble(args[0]);
        dataBase.countGreaterThanMetersAboveSeaLevel(metersAboveSeaLevel);
    }
}
