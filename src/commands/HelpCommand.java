package commands;

import collections.CSVDataBase;
import components.Response;
import utils.ResponseMachine;

public class HelpCommand extends CHCommand{
    public HelpCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        String resp = "";
        for (String item : args){
            resp = resp + item + "\n";
        }
        return ResponseMachine.makeClientResponse(resp);
    }
}
