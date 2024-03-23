package commands;

public class HistoryCommand extends CHCommand{
    public HistoryCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
}
