package validators;

import components.*;
import exceptions.WrongDataException;

import java.util.List;

public class CityCollectionValidator extends CollectionValidator {
    @Override
    public boolean validateData(String[] dataToValidate) {
        Human governor = null;
        Coordinates coords = null;
        try {
            governor = new Human(Integer.parseInt(dataToValidate[9]));
        } catch (NumberFormatException e) {
            System.out.println("Неверные данные: возраст человека должен быть целым положительным числом");
            return false;
        }
        try {
            coords = new Coordinates(Float.parseFloat(dataToValidate[1]),
                    Integer.parseInt(dataToValidate[2]));
        } catch (NumberFormatException e) {
            System.out.println("Неверные данные: координаты вводятся через пробел," +
                    " где первое число вещественное, а второе - целое");
            return false;
        }
        String governmentString = dataToValidate[7];
         Government government = switch (governmentString) {
            case ("DESPOTISM") -> Government.DESPOTISM;
            case ("DICTATORSHIP") -> Government.DICTATORSHIP;
            case ("STRATOCRACY") -> Government.STRATOCRACY;
            default -> null;
        };
        String climateString = dataToValidate[6];
        Climate climate = switch (climateString) {
            case ("MONSOON") -> Climate.MONSOON;
            case ("MEDITERRANIAN") -> Climate.MEDITERRANIAN;
            case ("SUBARCTIC") -> Climate.SUBARCTIC;
            case ("DESERT") -> Climate.DESERT;
            default -> null;
        };
        String standardOfLivingString = dataToValidate[8];
        StandardOfLiving standardOfLiving = switch (standardOfLivingString) {
            case ("VERY_HIGH") -> StandardOfLiving.VERY_HIGH;
            case ("LOW") -> StandardOfLiving.LOW;
            case ("NIGHTMARE") -> StandardOfLiving.NIGHTMARE;
            default -> null;
        };
        int area = Integer.parseInt(dataToValidate[3]);
        int population = Integer.parseInt(dataToValidate[4]);
        try {
            Double metersAboveSeaLevel = Double.parseDouble(dataToValidate[5]);
        }
        catch (NumberFormatException e){
            System.out.println("Неверные данные: Высота над уровнем моря должна быть вещественным числом.");
            return false;
        }
        CoordinatesValidator.validateData(coords);
        try {
            if (AreaValidator.validateData(area) &&
                    CityNameValidator.validateData(dataToValidate[0]) &&
                    GovernmentValidator.validateData(government) && GovernorValidator.validateData(governor)
                    && ClimateValidator.validateData(climate) && PopulationValidator.validateData(population) &&
                    StandardOfLivingValidator.validateData(standardOfLiving)){
                return true;
            }
            return false;
        }
        catch (WrongDataException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}

