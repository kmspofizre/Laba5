package validators;

import components.Coordinates;
import exceptions.WrongDataException;

public class CoordinatesValidator extends Validator{
    public static boolean validateData(Coordinates forValidation) throws WrongDataException{
        if (forValidation == null){
            throw new WrongDataException("Неверные данные: Поле с координатами пусто или заполнено неверно");
        }
        return true;
    }
}
