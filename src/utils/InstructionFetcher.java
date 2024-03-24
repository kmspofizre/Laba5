package utils;

import commands.CHCommand;
import commands.Command;
import commands.CommandHandler;
import exceptions.CommandExecutingException;

import java.util.Objects;

public class InstructionFetcher {
    private Command[] commandsAvalible;
    public InstructionFetcher(Command [] commands){
        this.commandsAvalible = commands;
    }
    public Command instructionFetch(String command) throws CommandExecutingException{
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
    public void fetchAndExecuteCHC(Command command, CommandHandler commandHandler){
        if ((Objects.equals(command.getCommandName(), "execute_command"))) {
            // исполнить скрипт
        } else if ((Objects.equals(command.getCommandName(), "help"))) {
            commandHandler.help();
        } else if ((Objects.equals(command.getCommandName(), "history"))) {
            commandHandler.getHistory();
        }
    }
}
