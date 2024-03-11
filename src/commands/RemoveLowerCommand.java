package commands;

import collections.CSVDataBase;

public class RemoveLowerCommand extends Command{

    public RemoveLowerCommand(String commandName, String description, boolean hasArguments) {
        super(commandName, description, hasArguments);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException{
        // валидация
        Long id = Long.parseLong(args[0]);
        dataBase.removeLower(id);
    }
}
