package utils;

import commands.CHCommand;
import commands.Command;
import commands.CommandHandler;
import exceptions.CommandExecutingException;

import java.io.FileNotFoundException;
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
    }
    public void fetchAndExecuteCHC(Command command,
                                   CommandHandler commandHandler,
                                   String[] args) throws CommandExecutingException,
            FileNotFoundException, StackOverflowError {
        if ((Objects.equals(command.getCommandName(), "execute_script"))) {
            if (args.length == 0){
                throw new CommandExecutingException("Не передано имя файла");
            }
            commandHandler.executeScript(args[0]);

        } else if ((Objects.equals(command.getCommandName(), "help"))) {
            commandHandler.help();
        } else if ((Objects.equals(command.getCommandName(), "history"))) {
            commandHandler.getHistory();
        }
    }
}
