package utils;

import commands.CHCommand;
import commands.Command;
import commands.CommandHandler;
import commands.ExecuteScriptCommand;
import components.Request;
import components.User;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;

import java.io.FileNotFoundException;
import java.util.*;

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
                                   String[] args, User user) throws CommandExecutingException,
            FileNotFoundException, StackOverflowError {
        if ((Objects.equals(command.getCommandName(), "execute_script"))) {
            if (args.length == 0){
                throw new CommandExecutingException("Не передано имя файла");
            }
            commandHandler.executeScript(args[0], user);

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
            HashSet<String> hashSet = new HashSet<>();
            List<String> stringList = ((ExecuteScriptCommand) command).prepareData(args, commands, hashSet);
            return ((ExecuteScriptCommand) command).prepareStringForRequest(stringList);
            // здесь нужно получить лист стрингов из скрипта и соединить их по \n
            // валидация скрипта с помощью prepareData в execute_script формирование большого запроса

        } else if ((Objects.equals(command.getCommandName(), "help"))) {
            for (Command currentCommand : commands){
                ResponseMachine.makeStringResponse(currentCommand.toString());
            }
            user.addCommandToHistory("help");
            return "";
        } else if ((Objects.equals(command.getCommandName(), "history"))) {
            user.getHistory();
            return "";
        }
        return null;
    }
    public List<Request> parseScript(String scriptCommands, InstructionFetcher infetch, User user){
        List<Request> requestList = new ArrayList<>();
        Scanner scanner = new Scanner(scriptCommands);
        String line;
        while (scanner.hasNextLine()){
            line = scanner.nextLine();
            String[] command = line.split(" ");
            String[] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            Command currentCommand = infetch.instructionFetch(command[0]);
            if (currentCommand == null | Objects.equals(line, "")){
                continue;
            }
            if (Objects.equals(currentCommand.getCommandName(), "undo")){
                continue;
            }
            if (Objects.equals(currentCommand.getCommandName(),
                    "help")){
                for (Command currentCommand1 : infetch.commandsAvalible){
                    ResponseMachine.makeStringResponse(currentCommand1.toString());
                }
                continue;
            }
            else if (Objects.equals(currentCommand.getCommandName(), "history")){
                user.getHistory();
                continue;
            }
            Request commandRequest = currentCommand.prepareRequest(argsToGive, scanner, true);
            RequestMachine.addCommandToRequest(commandRequest, currentCommand);
            requestList.add(commandRequest);
        }
        return requestList;
    }
}
