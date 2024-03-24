package commands;

import collections.CSVDataBase;

public class HelpCommand extends CHCommand{
    public HelpCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        for (String item : args){
            System.out.println(item);
        }
    }
}
