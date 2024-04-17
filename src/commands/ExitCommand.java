package commands;

import collections.CSVDataBase;
import utils.ResponseMachine;
import validators.InputDataValidator;

public class ExitCommand extends Command{
    public ExitCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        if (InputDataValidator.yesOrNo("Вы уверены, что хотите выйти? (YES/NO)")){
            ResponseMachine.makeStringResponse("До связи!");
            System.exit(0);
        }
    }
}
