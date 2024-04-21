package commands;

import collections.CSVDataBase;
import components.City;
import components.Response;
import exceptions.CommandExecutingException;

public abstract class DataBaseCommand extends Command{

    public DataBaseCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    public Response execute(String args, City city, CSVDataBase csvDataBase){
        return null;
    }

    public abstract Response execute(String[] args, City city, CSVDataBase dataBase, boolean fromScript) throws CommandExecutingException;
}
