package commands;

import collections.PostgresDataBase;
import components.Request;
import components.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SaveCommand extends Command{
    public SaveCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }

    @Override
    public Response execute(String [] args, PostgresDataBase dataBase, boolean fromScript){
        return dataBase.save();
    }

    @Override
    public Request prepareRequest(String [] args, Scanner scanner, boolean fromScript){
        return new Request(new String[0]);
    }
}
