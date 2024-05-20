package commands;


import collections.PostgresDataBase;
import components.City;
import components.Response;
import components.User;
import exceptions.CommandExecutingException;

import java.sql.SQLException;

public abstract class DataBaseCommand extends Command{

    public DataBaseCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    public Response execute(String[] args, City city, PostgresDataBase csvDataBase, User user) throws CommandExecutingException, SQLException {
        return null;
    }

}
