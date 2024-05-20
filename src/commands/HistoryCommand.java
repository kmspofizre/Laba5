package commands;


import collections.PostgresDataBase;
import components.Response;
import components.User;
import utils.ResponseMachine;

public class HistoryCommand extends CHCommand{
    public HistoryCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, PostgresDataBase dataBase, User user){
        String resp = "";
        for (String item : args){
            resp = resp + item + "\n";
        }
        return ResponseMachine.makeClientResponse(resp);
    }
}
