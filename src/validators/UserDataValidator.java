package validators;

import exceptions.WrongDataException;
import utils.ResponseMachine;

import java.util.Objects;
import java.util.Scanner;

public class UserDataValidator extends Validator {
    public static boolean validateNameData(String forValidation) throws WrongDataException {
        if ((forValidation.isEmpty()) | (forValidation == null) | (forValidation.equals("null")) | (forValidation.length() < 3)) {
            throw new WrongDataException("Неверные данные: Поле с логином не может быть пустым или короче 3 символов");
        }
        return true;
    }


    public static boolean validatePasswordData(String forValidation) throws WrongDataException {
        if ((forValidation.isEmpty()) | (forValidation == null) | (forValidation.equals("null")) | (forValidation.length() < 5)) {
            throw new WrongDataException("Неверные данные: Поле с паролем не может быть пустым или короче 5 символов");
        }
        return true;
    }

    public static String askNameData(String question, Scanner scanner) {
        String line = "";
        boolean validName = false;

        while (!validName) {
            try {
                ResponseMachine.makeStringResponse(question);
                line = scanner.nextLine();
                validName = validateNameData(line);
            } catch (WrongDataException e) {
                System.out.println(e.getMessage());
                validName = false;
            }
        }
        return line;
    }

    public static String askPasswordData(String question, Scanner scanner) {
        String line = "";
        boolean validName = false;

        while (!validName) {
            try {
                ResponseMachine.makeStringResponse(question);
                line = scanner.nextLine();
                validName = validatePasswordData(line);


            } catch (WrongDataException e) {
                System.out.println(e.getMessage());
                validName = false;
            }

        }
        return line;
    }
}
