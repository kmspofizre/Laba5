package utils;

import collections.CSVDataBase;
import commands.CHCommand;
import commands.Command;
import commands.CommandHandler;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ProgramRunner {
    private boolean fromScript;
    private CommandHandler commandHandler;
    private CSVDataBase csvDataBase;
    public ProgramRunner(CSVDataBase csvDataBase, CommandHandler commandHandler){
        this.csvDataBase = csvDataBase;
        this.commandHandler = commandHandler;
        this.fromScript = false;
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
                    infetch.fetchAndExecuteCHC(currentCommand, this.commandHandler, argsToGive);
                }
                else {
                    String [] argsForCommand = DataPreparer.prepareData(currentCommand, argsToGive, scanner, false);
                    this.commandHandler.executeCommand(currentCommand, argsForCommand, false);
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
