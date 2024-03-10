package validators;

import components.*;
import exceptions.WrongDataException;
import utils.CityCollectionMaker;


public class CityCollectionValidator extends CollectionValidator {
    @Override
    public boolean validateData(String[] dataToValidate) {
        Human governor = null;
        Coordinates coords = null;
        int area = 0;
        int population = 0;
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
            System.out.println("Неверные данные: координатами являются два числа," +
                    " где первое число вещественное, а второе - целое");
            return false;
        }
        String governmentString = dataToValidate[7];
        Government government = CityCollectionMaker.getGovernment(governmentString);
        String climateString = dataToValidate[6];

        Climate climate = CityCollectionMaker.getClimate(climateString);
        String standardOfLivingString = dataToValidate[8];
        StandardOfLiving standardOfLiving = CityCollectionMaker.getStandardOfLiving(standardOfLivingString);
        try {
            area = Integer.parseInt(dataToValidate[3]);
        }
        catch (NumberFormatException e){
            System.out.println("Неверные данные: значение площади должно быть числом большим нуля");
            return false;
        }
        try {
            population = Integer.parseInt(dataToValidate[4]);
        }
        catch (NumberFormatException e){
            System.out.println("Неверные данные: Население должно быть числом большим нуля");
            return false;
        }

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
    public static boolean numberOfParamsValidator(String [] items){
        return (items.length == 10);
    }
}

