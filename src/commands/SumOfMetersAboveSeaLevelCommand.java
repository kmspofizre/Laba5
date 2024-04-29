package commands;

import collections.CSVDataBase;
import components.Request;
import components.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SumOfMetersAboveSeaLevelCommand extends Command{
    public SumOfMetersAboveSeaLevelCommand(String commandName, String description,
                                           boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }

    @Override
    public Response execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        return dataBase.sumOfMetersAboveSeaLevel();
    }

    @Override
    public Request prepareRequest(String [] args, Scanner scanner, boolean fromScript){
        return new Request(new String[0]);
    }
}
