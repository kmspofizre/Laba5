package utils;

import collections.CSVDataBase;
import commands.CHCommand;
import commands.Command;
import commands.CommandHandler;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;

import java.util.Arrays;
import java.util.Scanner;

public class ProgramRunner {
    private boolean fromScript;
    private CommandHandler commandHandler;
    private CSVDataBase csvDataBase;
    public ProgramRunner(CSVDataBase csvDataBase, CommandHandler commandHandler){
        this.csvDataBase = csvDataBase;
        this.commandHandler = commandHandler;
    }
    // fromScript
    public void runProgram(){
        Scanner scanner = new Scanner(System.in);
        InstructionFetcher infetch = new InstructionFetcher(this.commandHandler.getCommands());
        while (true){
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            String [] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            try {
                Command currentCommand = infetch.instructionFetch(command[0]);
                if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                    infetch.fetchAndExecuteCHC(currentCommand, this.commandHandler);
                }
                else {
                    String [] argsForCommand = DataPreparer.prepareData(currentCommand, argsToGive, scanner);
                    this.commandHandler.executeCommand(currentCommand, argsForCommand, false);
                }
            }
            catch (CommandExecutingException | WrongDataException | NumberFormatException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
