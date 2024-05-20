package commands;


import collections.PostgresDataBase;
import components.City;
import components.Response;
import exceptions.CommandExecutingException;

import java.sql.SQLException;

public abstract class DataBaseCommand extends Command{

    public DataBaseCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    public Response execute(String args, City city, PostgresDataBase csvDataBase){
        return null;
    }

    public abstract Response execute(String[] args, City city, PostgresDataBase dataBase, boolean fromScript) throws CommandExecutingException, SQLException;
}
