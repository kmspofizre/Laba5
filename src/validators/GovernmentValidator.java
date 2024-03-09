package validators;

import components.Government;
import exceptions.WrongDataException;

public class GovernmentValidator extends Validator{
    public static boolean validateData(Government forValidation) throws WrongDataException{

        if (forValidation == null){
            throw new WrongDataException("Неверные данные: Поле 'Правительство' пусто или заполнено неверно.\nНужно выбрать из списка");
        }
        return true;
    }
}
