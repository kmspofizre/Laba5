package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CountGreaterThanMetersAboveSeaLevel extends Command{
    public CountGreaterThanMetersAboveSeaLevel(String commandName, String description,
                                               boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript) {
        Double metersAboveSeaLevel = Double.parseDouble(args[0]);
        dataBase.countGreaterThanMetersAboveSeaLevel(metersAboveSeaLevel);
    }
    @Override
    public String[] prepareData(String [] args, Scanner scanner) throws NumberFormatException{
        String [] data = new String [1];
        Double metersAboveSeaLevel = Double.parseDouble(args[0]);
        data[0] = args[0];
        return data;
    }
}
