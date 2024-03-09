package validators;

import exceptions.WrongDataException;

public class AreaValidator extends Validator{
    public static boolean validateData(int forValidation) throws WrongDataException{
        if (forValidation <= 0){
            throw new WrongDataException("Неверные данные: значение площади должно быть больше нуля");
        }
        return true;
    }
}
