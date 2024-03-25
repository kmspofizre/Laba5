package validators;

import components.Climate;
import exceptions.WrongDataException;

public class ClimateValidator extends Validator{
    public static boolean validateData(Climate forValidation) throws WrongDataException{

        if (forValidation == null){
            throw new WrongDataException("Неверные данные: Поле 'Климат' пусто или заполнено неверно.\nНужно выбрать из списка: MONSOON, MEDITERRANIAN, SUBARCTIC, DESERT");
        }
        return true;
    }
}
