package commands;

import collections.CSVDataBase;
import validators.InputDataValidator;

public class ExitCommand extends Command{
    public ExitCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
    @Override
    public void execute(String [] args, CSVDataBase dataBase, boolean fromScript){
        if (InputDataValidator.yesOrNo("Вы уверены, что хотите выйти? (YES/NO)")){
            System.out.println("До связи!");
            System.exit(0);
        }
    }
}
