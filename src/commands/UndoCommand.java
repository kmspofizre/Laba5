package commands;

import components.Request;

import java.util.Scanner;

public class UndoCommand extends Command{

    public UndoCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }

    @Override
    public Request prepareRequest(String[] args, Scanner scanner, boolean fromScript){
        return new Request(new String[0]);
    }
}
