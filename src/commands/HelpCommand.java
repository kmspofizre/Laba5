package commands;

public class HelpCommand extends CHCommand{
    public HelpCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
}
