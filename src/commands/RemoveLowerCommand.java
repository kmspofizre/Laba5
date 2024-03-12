package commands;

import collections.CSVDataBase;

public class RemoveLowerCommand extends Command{

    public RemoveLowerCommand(String commandName, String description,
                              boolean hasArguments, boolean isMultiLines) {
        super(commandName, description, hasArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        dataBase.removeLower(id);
    }
}
