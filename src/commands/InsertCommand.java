package commands;

import collections.CSVDataBase;
import components.City;
import components.CityRequest;
import components.Request;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.CityCollectionMaker;
import validators.InputDataValidator;

import java.util.*;

public class InsertCommand extends DataBaseCommand{
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
    public String[] prepareData(String [] args, Scanner scanner) throws NumberFormatException, WrongDataException {
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
    @Override
    public String[] prepareScriptData(String [] args,
                                      Scanner scanner) throws NumberFormatException, NoSuchElementException {
        Long id = Long.parseLong(args[0]);
        String[] data = new String[11];
        data[0] = id.toString();
        for (int i = 1; i < 11; i++){
            data[i] = scanner.nextLine();
        }
        return data;
    }
    @Override
    public Request prepareRequest(String [] args, Scanner scanner){
        String[] cityData = prepareData(args, scanner);
        City city = CityCollectionMaker.makeCityInstance(cityData);
        CityRequest cityRequest = new CityRequest(args[0], city);
        return cityRequest;
    }
}
