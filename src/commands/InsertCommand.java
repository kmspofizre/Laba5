package commands;

import collections.CSVDataBase;
import exceptions.CommandExecutingException;
import validators.InputDataValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InsertCommand extends Command{
    public InsertCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript) throws CommandExecutingException{
        Long id = Long.parseLong(args[0]);
        String [] argsToGive = Arrays.copyOfRange(args, 0, args.length);
        if (argsToGive.length <= 10){
            throw new CommandExecutingException("Недостаточно данных");
        }
        List<String []> commandArgs = new ArrayList<>();
        commandArgs.add(argsToGive);
        dataBase.insert(id, commandArgs, fromScript);
    }

    @Override
    public String[] prepareData(String [] args, Scanner scanner) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        String [] data = new String[11];
        data[0] = id.toString();
        data[1] = InputDataValidator.stringDataValidator(scanner);
        data[2] = InputDataValidator.xCoordinateValidator(scanner);
        data[3] = InputDataValidator.yCoordinateValidator(scanner);
        data[4] = InputDataValidator.areaValidator(scanner);
        data[5] = InputDataValidator.populationValidator(scanner);
        data[6] = InputDataValidator.metersAboveSeaLevelValidator(scanner);
        data[7] = InputDataValidator.climateValidator(scanner);
        data[8] = InputDataValidator.governmentValidator(scanner);
        data[9] = InputDataValidator.standardOfLivingValidator(scanner);
        data[10] = InputDataValidator.governorValidator(scanner);
        return data;
    }
}
