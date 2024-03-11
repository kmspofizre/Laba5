package commands;

import collections.CSVDataBase;

public class Command {
    private String commandName;
    private String description;
    private boolean hasArguments;
    public Command(String commandName, String description, boolean hasArguments){
        this.commandName = commandName;
        this.description = description;
        this.hasArguments = hasArguments;
    }
    public void execute(String [] args, CSVDataBase dataBase){}
    @Override
    public String toString(){
        return this.commandName + " - " + this.description;
    }
    public String getCommandName(){
        return this.commandName;
    }
}
