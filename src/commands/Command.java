package commands;

import collections.CSVDataBase;
import components.Request;
import components.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Command implements Serializable {
    private String commandName;
    private String description;
    private boolean hasInlineArguments;
    private boolean isMultiLines;
    private static final long serialVersionUID = 228337L;
    public Command(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines){
        this.commandName = commandName;
        this.description = description;
        this.hasInlineArguments = hasInlineArguments;
        this.isMultiLines = isMultiLines;
    }
    public Response execute(String [] args, CSVDataBase dataBase, boolean fromScript){return null;}
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
    public Request prepareRequest(String [] args, Scanner scanner){
        return null;
    }
}
