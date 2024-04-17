package utils;

import commands.CHCommand;
import commands.Command;
import commands.CommandHandler;
import components.User;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;

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


    public String fetchAndCheckCHC(Command command, String[] args, Command [] commands, User user) throws CommandExecutingException,
            FileNotFoundException, StackOverflowError, WrongDataException {
        if ((Objects.equals(command.getCommandName(), "execute_script"))) {
            if (args.length == 0){
                throw new CommandExecutingException("Не передано имя файла");
            }
            // здесь нужно получить лист стрингов из скрипта и соединить их по \n
            // валидация скрипта с помощью prepareData в execute_script формирование большого запроса
            return null;

        } else if ((Objects.equals(command.getCommandName(), "help"))) {
            for (Command currentCommand : commands){
                ResponseMachine.makeStringResponse(currentCommand.toString());
            }
            user.addCommandToHistory("help");
            return null;
        } else if ((Objects.equals(command.getCommandName(), "history"))) {
            user.getHistory();
            return null;
        }
        return null;
    }
}
