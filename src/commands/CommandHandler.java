package commands;

import collections.CSVDataBase;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.DataPreparer;
import utils.InstructionFetcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CommandHandler {
    private CSVDataBase dataBase;
    private Command [] commands;
    private String [] history;
    private int historyIndex;
    private InstructionFetcher instructionFetcher;
    public CommandHandler(CSVDataBase dataBase, Command [] commands){
        this.dataBase = dataBase;
        this.commands = commands;
        this.history = new String [14];
        this.historyIndex = 0;
        this.instructionFetcher = new InstructionFetcher(commands);
    }
    public boolean executeCommand(Command command, String [] args, boolean fromScript){
        try{
            command.execute(args, this.dataBase, fromScript);
            this.history[this.historyIndex] = command.getCommandName();
            incrementHistoryIndex();
            return true;
        }
        catch (CommandExecutingException e){
            System.out.println(e.getMessage());
            return false;
        }
        catch (NumberFormatException e){
            System.out.println("Неверный формат ввода числового значения");
            return false;
        }
    }
    public void help(){
        for (Command command : this.commands){
            System.out.println(command.toString());
        }
        this.history[this.historyIndex] = "help";
        incrementHistoryIndex();
    }
    public void getHistory(){
        System.out.println("Последние вызванные команды:");
        for (String command : this.history){
            if (command != null){
                System.out.println(command);
            }
        }
        this.history[this.historyIndex] = "history";
        incrementHistoryIndex();
    }
    private void incrementHistoryIndex(){
        if (this.historyIndex == 14){
            this.historyIndex = 0;
        }
        else {
            this.historyIndex += 1;
        }
    }
    public Command[] getCommands() {
        return commands;
    }
    public void executeScript(String fileName) throws FileNotFoundException, StackOverflowError {
        Scanner scanner = new Scanner(new File(fileName));
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] command = line.split(" ");
            String [] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            try {
                Command currentCommand = this.instructionFetcher.instructionFetch(command[0]);
                if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                    if (Objects.equals(currentCommand.getCommandName(), "execute_script")){
                        if (argsToGive.length == 0){
                            throw new CommandExecutingException("Не передано имя файла");
                        }
                        executeScript(argsToGive[0]);
                    }
                    else if (Objects.equals(currentCommand.getCommandName(), "history")){
                        getHistory();
                    }
                    else if (Objects.equals(currentCommand.getCommandName(), "help")){
                        help();
                    }
                }
                else {
                    String [] argsForCommand = DataPreparer.prepareScriptData(currentCommand, argsToGive, scanner);
                    executeCommand(currentCommand, argsForCommand, true);
                }
            }
            catch (CommandExecutingException | WrongDataException e){
                System.out.println(e.getMessage());
            }
            catch (NumberFormatException exc){
                System.out.println("Неверный формат ввода числового значения");
            }
            catch (FileNotFoundException fnfe){
                System.out.println("Передано неверное имя файла");
            }
            catch (StackOverflowError sofe){
                System.out.println("Вызов скрипта повлек рекурсию");
            }
            catch (NoSuchElementException e){
                System.out.println("Недостаточно данных");
            }
        }
        scanner.close();
    }
}
