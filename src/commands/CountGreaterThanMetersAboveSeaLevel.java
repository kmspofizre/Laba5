package commands;


import collections.PostgresDataBase;
import components.Request;
import components.Response;
import components.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CountGreaterThanMetersAboveSeaLevel extends Command{
    public CountGreaterThanMetersAboveSeaLevel(String commandName, String description,
                                               boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public Response execute(String [] args, PostgresDataBase dataBase, User user) throws SQLException {
        Double metersAboveSeaLevel = Double.parseDouble(args[0]);
        return dataBase.countGreaterThanMetersAboveSeaLevel(metersAboveSeaLevel);
    }
    @Override
    public String[] prepareData(String [] args, Scanner scanner, boolean fromScript) throws NumberFormatException{
        String [] data = new String [1];
        Double metersAboveSeaLevel = Double.parseDouble(args[0]);
        data[0] = args[0];
        return data;
    }

    @Override
    public String[] prepareScriptData(String [] args, Scanner scanner) throws NumberFormatException{
        String [] data = new String [1];
        Double metersAboveSeaLevel = Double.parseDouble(args[0]);
        data[0] = args[0];
        return data;
    }


    @Override
    public Request prepareRequest(String [] args, Scanner scanner, boolean fromScript){
        String [] data = prepareData(args, scanner, fromScript);
        return new Request(data);
    }
}
