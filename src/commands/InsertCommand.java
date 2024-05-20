package commands;


import collections.PostgresDataBase;
import components.*;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.CityCollectionMaker;
import validators.InputDataValidator;

import java.sql.SQLException;
import java.util.*;

public class InsertCommand extends DataBaseCommand implements Reversible {
    public InsertCommand(String name, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(name, description, hasInlineArguments, isMultiLines);
    }

    @Override
    public Response execute(String [] args, City city, PostgresDataBase dataBase, User user) throws CommandExecutingException, SQLException {
        Long id = Long.parseLong(args[0]);
        String [] argsToGive = Arrays.copyOfRange(args, 0, args.length);
        List<String []> commandArgs = new ArrayList<>();
        commandArgs.add(argsToGive);
        Response response = dataBase.insert(city, user);
        return response;
    }

    @Override
    public String[] prepareData(String [] args, Scanner scanner, boolean fromScript) throws NumberFormatException, WrongDataException {
        Long id = Long.parseLong(args[0]);
        String [] data = new String[11];
        data[0] = id.toString();
        data[1] = InputDataValidator.stringDataValidator(scanner, fromScript);
        data[2] = InputDataValidator.xCoordinateValidator(scanner, fromScript);
        data[3] = InputDataValidator.yCoordinateValidator(scanner, fromScript);
        data[4] = InputDataValidator.areaValidator(scanner, fromScript);
        data[5] = InputDataValidator.populationValidator(scanner, fromScript);
        data[6] = InputDataValidator.metersAboveSeaLevelValidator(scanner, fromScript);
        data[7] = InputDataValidator.climateValidator(scanner, fromScript);
        data[8] = InputDataValidator.governmentValidator(scanner, fromScript);
        data[9] = InputDataValidator.standardOfLivingValidator(scanner, fromScript);
        data[10] = InputDataValidator.governorValidator(scanner, fromScript);
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
    public Request prepareRequest(String [] args, Scanner scanner, boolean fromScript){
        String[] cityData = prepareData(args, scanner, fromScript);
        City city = CityCollectionMaker.makeCityInstance(cityData);
        CityRequest cityRequest = new CityRequest(args, city);
        return cityRequest;
    }
    @Override
    public Response undo(TreeMap<Long, City> lastAction, PostgresDataBase csvDataBase){
        Response response = csvDataBase.removeAllFromCollection(lastAction);
        return response;
    }
}
