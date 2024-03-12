package commands;

import collections.CSVDataBase;

public class ExitCommand extends Command{
    public ExitCommand(String commandName, String description, boolean hasArguments, boolean isMultiLines) {
        super(commandName, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase){
        System.out.println("До связи!");
        System.exit(0);
    }
}
