package utils;

import commands.Command;
import exceptions.CommandExecutingException;

public class InstructionFetcher {
    private Command[] commandsAvalible;
    public InstructionFetcher(Command [] commands){
        this.commandsAvalible = commands;
    }
    public Command instructionFetch(String command, String[] firstLineArgs) throws CommandExecutingException{
        for (Command item : this.commandsAvalible){
            if (item.getCommandName().equals(command)){
                return item;
            }
        }
        throw new CommandExecutingException("Неверная команда. Используйте help, чтобы узнать список доступных команд");
        // если длинная, то передать true и вызвать методы из InputDataValidator

        // если короткая, то передать false и обработать inline значение
        // выкинуть исключение, если нет команжы
    }
}
