package commands;

import collections.CSVDataBase;
import utils.ResponseMachine;

public class HelpCommand extends CHCommand{
    public HelpCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        for (String item : args){
            ResponseMachine.makeStringResponse(item);
        }
    }
}
