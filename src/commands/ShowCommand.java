package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowCommand extends Command{
    public ShowCommand(String commandName, String description, boolean hasArguments) {
        super(commandName, description, hasArguments);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase){
        dataBase.show();
    }
}
