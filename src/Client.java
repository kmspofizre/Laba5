import commands.CHCommand;
import commands.Command;
import components.User;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.CommandsInitiator;
import utils.DataPreparer;
import utils.InstructionFetcher;
import utils.ResponseMachine;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void client(String [] args) {
        // init
        // считывание ввода через while
        // получение команды
        // валидация (сначала команда, потом переданные значения)
        // если все норм, формируем request в классе request есть user
        // если нет, валидатор выдаст ошибку
        Scanner scanner = new Scanner(System.in);
        Command[] commands = CommandsInitiator.initCommands();
        InstructionFetcher infetch = new InstructionFetcher(commands);
        User user = new User();
        while (true) {
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            String[] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            try {
                Command currentCommand = infetch.instructionFetch(command[0]);
                if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                    String commandData = infetch.fetchAndCheckCHC(currentCommand, argsToGive, commands, user);
                    if (currentCommand.getCommandName().equals("execute_script")){
                        // отправить полученную большую строку на серв
                    }
                }
                else {
                    String [] argsForCommand = DataPreparer.prepareData(currentCommand, argsToGive, scanner);
                    // отправка собранных значений на сервер (здесь это для одной команды)
                    // this.commandHandler.executeCommand(currentCommand, argsForCommand, false);
                }
            }
            catch (CommandExecutingException | WrongDataException e){
                ResponseMachine.makeStringResponse(e.getMessage());
            }
            catch (NumberFormatException exc){
                ResponseMachine.makeStringResponse("Неверный формат ввода числового значения");
            }
            catch (FileNotFoundException fnfe){
                ResponseMachine.makeStringResponse("Передано неверное имя файла");
            }
        }
    }
}
