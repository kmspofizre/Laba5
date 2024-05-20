package commands;

import collections.PostgresDataBase;
import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.DataPreparer;
import utils.InstructionFetcher;
import utils.ResponseMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;

public class CommandHandler {
    private PostgresDataBase dataBase;
    private Command [] commands;
    private String [] history;
    private int historyIndex;
    private InstructionFetcher instructionFetcher;
    public CommandHandler(PostgresDataBase dataBase, Command [] commands){
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
            ResponseMachine.makeStringResponse(e.getMessage());
            return false;
        }
        catch (NumberFormatException e){
            ResponseMachine.makeStringResponse("Неверный формат ввода числового значения");
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void help(){
        for (Command command : this.commands){
            ResponseMachine.makeStringResponse(command.toString());
        }
        this.history[this.historyIndex] = "help";
        incrementHistoryIndex();
    }
    public void getHistory(){
        ResponseMachine.makeStringResponse("Последние вызванные команды:");
        for (String command : this.history){
            if (command != null){
                ResponseMachine.makeStringResponse(command);
            }
        }
        this.history[this.historyIndex] = "history";
        incrementHistoryIndex();
    }
    private void incrementHistoryIndex(){
        if (this.historyIndex == 13){
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
            if (Objects.equals(line, "")){
                continue;
            }
            String[] command = line.split(" ");
            String [] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            try {
                Command currentCommand = this.instructionFetcher.instructionFetch(command[0]);
                if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                    if (Objects.equals(currentCommand.getCommandName(), "execute_script")){
                        if (argsToGive.length == 0){
                            throw new CommandExecutingException("Не передано имя файла");
                        }
                        this.dataBase.save();
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
                ResponseMachine.makeStringResponse(e.getMessage());
            }
            catch (NumberFormatException exc){
                ResponseMachine.makeStringResponse("Неверный формат ввода числового значения");
            }
            catch (FileNotFoundException fnfe){
                ResponseMachine.makeStringResponse("Передано неверное имя файла");
            }
            catch (StackOverflowError sofe){
                ResponseMachine.makeStringResponse("Вызов скрипта повлек рекурсию");
            }
            catch (NoSuchElementException e){
                ResponseMachine.makeStringResponse("Недостаточно данных");
            }
        }
        this.dataBase.save();
        scanner.close();
    }
}
