package utils;


import collections.PostgresDataBase;
import commands.CHCommand;
import commands.Command;
import commands.CommandHandler;
import components.User;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ProgramRunner {
    private boolean fromScript;
    private CommandHandler commandHandler;
    private PostgresDataBase csvDataBase;
    private User user;
    public ProgramRunner(PostgresDataBase csvDataBase, CommandHandler commandHandler, User user){
        this.csvDataBase = csvDataBase;
        this.commandHandler = commandHandler;
        this.fromScript = false;
        this.user = user;
    }
    // fromScript
    public void runProgram(){
        Scanner scanner = new Scanner(System.in);
        InstructionFetcher infetch = new InstructionFetcher(this.commandHandler.getCommands());
        while (true){
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            String [] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            // здесь происходит передача на сервер и там происходит обработка команды
            // все, что ниже - нужно перенести на сервер
            try {
                Command currentCommand = infetch.instructionFetch(command[0]);
                if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                    infetch.fetchAndExecuteCHC(currentCommand, this.commandHandler, argsToGive, this.user);
                }
                else {
                    String [] argsForCommand = DataPreparer.prepareData(currentCommand, argsToGive, scanner, false);
                    this.commandHandler.executeCommand(currentCommand, argsForCommand, this.user);
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
            catch (StackOverflowError sofe){

            }
        }
    }
}
