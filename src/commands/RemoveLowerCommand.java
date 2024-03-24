package commands;

import collections.CSVDataBase;

import java.util.Scanner;

public class RemoveLowerCommand extends Command{

    public RemoveLowerCommand(String commandName, String description,
                              boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        dataBase.removeLower(id);
    }
    @Override
    public String[] prepareData(String [] args, Scanner scanner) throws NumberFormatException{
        String [] data = new String [1];
        Long id = Long.parseLong(args[0]);
        data[0] = args[0];
        return data;
    }
}
