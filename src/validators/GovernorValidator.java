package validators;

import components.Human;
import exceptions.WrongDataException;

public class GovernorValidator extends Validator{
    public static boolean validateData(Human forValidation) throws WrongDataException{

        if (forValidation == null){
            throw new WrongDataException("Неверные данные: Поле 'Губернатор' пусто или заполнено неверно");
        }
        return true;
    }
}
