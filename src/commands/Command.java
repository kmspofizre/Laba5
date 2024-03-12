package commands;

import collections.CSVDataBase;

public class Command {
    private String commandName;
    private String description;
    private boolean hasInlineArguments;
    private boolean isMultiLines;
    public Command(String commandName, String description, boolean hasArguments, boolean isMultiLines){
        this.commandName = commandName;
        this.description = description;
        this.hasInlineArguments = hasArguments;
        this.isMultiLines = isMultiLines;
    }
    public void execute(String [] args, CSVDataBase dataBase){}
    @Override
    public String toString(){
        return this.commandName + " - " + this.description;
    }
    public String getCommandName(){
        return this.commandName;
    }
    public boolean isHasInlineArguments(){
        return this.hasInlineArguments;
    }
    public boolean isMultiLines(){
        return this.isMultiLines;
    }
}
