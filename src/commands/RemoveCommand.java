package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RemoveCommand extends Command{
    public RemoveCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        Long id = Long.parseLong(args[0]);
        dataBase.remove(id);
    }
    @Override
    public String[] prepareData(String [] args, Scanner scanner) throws NumberFormatException{
        String [] data = new String [1];
        Long id = Long.parseLong(args[0]);
        data[0] = args[0];
        return data;
    }
}
