package commands;

import collections.CSVDataBase;

public class ExitCommand extends Command{
    public ExitCommand(String commandName, String description, boolean hasArguments) {
        super(commandName, description, hasArguments);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase){
        System.out.println("До связи!");
        System.exit(0);
    }
}
