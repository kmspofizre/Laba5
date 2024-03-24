package commands;

import collections.CSVDataBase;

import java.util.ArrayList;
import java.util.Scanner;

public class Command {
    private String commandName;
    private String description;
    private boolean hasInlineArguments;
    private boolean isMultiLines;
    public Command(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines){
        this.commandName = commandName;
        this.description = description;
        this.hasInlineArguments = hasInlineArguments;
        this.isMultiLines = isMultiLines;
    }
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){}
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
    public String [] prepareData(String [] args, Scanner scanner){
        String [] data = new String[0];
        return data;
    }
    public String [] prepareScriptData(String[] args, Scanner scanner){
        String [] data = new String[0];
        return data;
    }
}
