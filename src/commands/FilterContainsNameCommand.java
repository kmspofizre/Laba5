package commands;

import collections.CSVDataBase;
import exceptions.WrongDataException;
import validators.CityNameValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FilterContainsNameCommand extends Command{
    public FilterContainsNameCommand(String commandName, String description,
                                     boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        String name = args[0];
        // валидация
        dataBase.filterContainsName(name);
    }
    @Override
    public String [] prepareData(String [] args, Scanner scanner) throws WrongDataException {
        String [] data = new String[1];
        if (CityNameValidator.validateData(args[0])){
            data[0] = args[0];
        }
        return data;
    }
}
