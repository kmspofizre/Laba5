package commands;

import collections.CSVDataBase;
import components.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ShowCommand extends Command{
    public ShowCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        dataBase.show();
    }


    @Override
    public Request prepareRequest(String [] args, Scanner scanner){
        return new Request("");
    }
}
