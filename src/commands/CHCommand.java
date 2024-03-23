package commands;

public class CHCommand extends Command{
    private String commandName;
    private String description;
    private boolean hasInlineArguments;
    private boolean isMultiLines;
    public CHCommand(String commandName, String description, boolean hasInlineArguments, boolean isMultiLines){
        super(commandName, description, hasInlineArguments, isMultiLines);
    }
}
