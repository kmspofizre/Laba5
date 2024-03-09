package validators;

import exceptions.WrongDataException;

public class PopulationValidator extends Validator{
    public static boolean validateData(int forValidation) throws WrongDataException{

        if (forValidation <= 0){
            throw new WrongDataException("Неверные данные: Население должно быть больше нуля");
        }
        return true;
    }
}
