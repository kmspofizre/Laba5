package commands;


import collections.PostgresDataBase;
import components.Request;
import components.Response;
import components.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InfoCommand extends Command{
    public InfoCommand(String commandName, String description,
                       boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, PostgresDataBase dataBase, User user){
        return dataBase.info();
    }

    @Override
    public Request prepareRequest(String [] args, Scanner scanner, boolean fromScript){
        return new Request(new String[0]);
    }
}
