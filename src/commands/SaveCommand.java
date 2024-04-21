package commands;

import collections.CSVDataBase;
import components.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SaveCommand extends Command{
    public SaveCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }

    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        dataBase.save();
    }

    @Override
    public Request prepareRequest(String [] args, Scanner scanner){
        return new Request("");
    }
}
