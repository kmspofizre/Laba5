package utils;

import commands.*;

public class CommandsInitiator {
    public static Command[] initCommands(){
        Command [] commands = new Command[11];
        commands[0] = new InfoCommand("info", "des", false);
        commands[1] = new ShowCommand("show", "des", false);
        commands[2] = new InsertCommand("insert", "des", true);
        commands[3] = new UpdateCommand("update", "des", true);
        commands[4] = new RemoveCommand("remove", "des", true);
        commands[5] = new ClearCommand("clear", "des", false);
        commands[6] = new SaveCommand("save", "des", false);
        commands[7] = new ExitCommand("exit", "des", false);
        commands[8] = new RemoveLowerCommand("remove_lower", "des", true);
        commands[9] = new RemoveGreaterKeyCommand("remove_greater_key",
                "des", false);
        commands[10] = new CountGreaterThanMetersAboveSeaLevel("count_greater_than_meters_above_sea_level",
                "des", true);
        commands[11] = new FilterContainsNameCommand("filter_contains_name",
                "des", true);
        return commands;
    }
}
