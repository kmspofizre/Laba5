package commands;

public class ExecuteScriptCommand extends CHCommand{
    public ExecuteScriptCommand(String commandName, String description,
                                boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }

}
