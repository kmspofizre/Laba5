package validators;

import exceptions.WrongDataException;

public class CityNameValidator extends Validator{
    public static boolean validateData(String forValidation) throws WrongDataException{
        if ((forValidation.isEmpty()) | (forValidation == null) | (forValidation.equals("null"))){
            throw new WrongDataException("Неверные данные: Поле с именем не может быть пустым");
        }
        return true;
    }
}
