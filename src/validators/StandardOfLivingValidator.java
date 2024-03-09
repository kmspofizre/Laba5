package validators;

import components.StandardOfLiving;
import exceptions.WrongDataException;

public class StandardOfLivingValidator extends Validator{
    public static boolean validateData(StandardOfLiving forValidation) throws WrongDataException{
        if (forValidation == null){
            throw new WrongDataException("Неверные данные: Поле 'Уровень жизни' пусто или заполнено неверно.\nНужно выбрать из списка");
        }
        return true;
    }
}
