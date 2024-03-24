package commands;

import collections.CSVDataBase;
import exceptions.CommandExecutingException;

import java.util.TreeMap;

public class CommandHandler {
    private CSVDataBase dataBase;
    private Command [] commands;
    private String [] history;
    private int historyIndex;
    public CommandHandler(CSVDataBase dataBase, Command [] commands){
        this.dataBase = dataBase;
        this.commands = commands;
        this.history = new String [14];
        this.historyIndex = 0;
    }
    public boolean executeCommand(Command command, String [] args, boolean fromScript){
        try{
            command.execute(args, this.dataBase, fromScript);
            this.history[this.historyIndex] = command.getCommandName();
            if (this.historyIndex == 14){
                this.historyIndex = 0;
            }
            else {
                this.historyIndex += 1;
            }
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
        if (this.historyIndex == 14){
            this.historyIndex = 0;
        }
        else {
            this.historyIndex += 1;
        }
    }
    public void getHistory(){
        System.out.println("Последние вызванные команды:");
        for (String command : this.history){
            if (command != null){
                System.out.println(command);
            }
        }
        this.history[this.historyIndex] = "history";
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
}
