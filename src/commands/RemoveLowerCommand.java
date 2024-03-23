package commands;

import collections.CSVDataBase;

public class RemoveLowerCommand extends Command{

    public RemoveLowerCommand(String commandName, String description,
                              boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript) throws NumberFormatException{
        Long id = Long.parseLong(args[0]);
        dataBase.removeLower(id);
    }
}
