package utils;

import commands.*;

public class CommandsInitiator {
    public static Command[] initCommands() {
        Command[] commands = new Command[16];
        commands[0] = new InfoCommand("info", "Вывести информацию о коллекции", false, false);
        commands[1] = new ShowCommand("show", "Вывести элементы коллекции", false, false);
        commands[2] = new InsertCommand("insert", "Добавить элемент с заданным ключом в коллекцию", true, true);
        commands[3] = new UpdateCommand("update", "Изменить элемент по заданному id", true, true);
        commands[4] = new RemoveCommand("remove", "Удалить элемент по заданному id", true, false);
        commands[5] = new ClearCommand("clear", "Очистить коллекцию", false, false);
        commands[6] = new SaveCommand("save", "Сохранить коллекцию в файл", false, false);
        commands[7] = new ExitCommand("exit", "Выйти из программы", false, false);
        commands[8] = new RemoveLowerCommand("remove_lower", "Удалить элементы коллекции, меньшие заданного", true, false);
        commands[9] = new RemoveGreaterKeyCommand("remove_greater_key",
                "Удалить все элементы коллекции, ключ которых превышает заданный", true, false);
        commands[10] = new CountGreaterThanMetersAboveSeaLevel("count_greater_than_meters_above_sea_level",
                "Вывести количество элементов, значение поля metersAboveSeaLevel которых больше заданного", true, false);
        commands[11] = new FilterContainsNameCommand("filter_contains_name",
                "Вывести элементы, значение поля name которых содержит заданную подстроку", true, false);
        commands[12] = new ExecuteScriptCommand("execute_script", "des", true, false);
        commands[13] = new HelpCommand("help", "Вывести справку по доступным командам", false, false);
        commands[14] = new HistoryCommand("history", "Вывести последние использованные команды", false, false);
        commands[15] = new SumOfMetersAboveSeaLevelCommand("sum_of_meters_above_sea_level",
                "Вывести сумму значений поля metersAboveSeaLevel для всех элементов коллекции",
                false, false);
        return commands;
    }
    public static Command[] initClientCommands() {
        Command[] commands = new Command[15];
        commands[0] = new InfoCommand("info", "Вывести информацию о коллекции", false, false);
        commands[1] = new ShowCommand("show", "Вывести элементы коллекции", false, false);
        commands[2] = new InsertCommand("insert", "Добавить элемент с заданным ключом в коллекцию", true, true);
        commands[3] = new UpdateCommand("update", "Изменить элемент по заданному id", true, true);
        commands[4] = new RemoveCommand("remove", "Удалить элемент по заданному id", true, false);
        commands[5] = new ClearCommand("clear", "Очистить коллекцию", false, false);

        commands[6] = new ExitCommand("exit", "Выйти из программы", false, false);
        commands[7] = new RemoveLowerCommand("remove_lower", "Удалить элементы коллекции, меньшие заданного", true, false);
        commands[8] = new RemoveGreaterKeyCommand("remove_greater_key", "Удалить все элементы коллекции, ключ которых превышает заданный", true, false);
        commands[9] = new CountGreaterThanMetersAboveSeaLevel("count_greater_than_meters_above_sea_level",
                "Вывести количество элементов, значение поля metersAboveSeaLevel которых больше заданного", true, false);
        commands[10] = new FilterContainsNameCommand("filter_contains_name",
                "Вывести элементы, значение поля name которых содержит заданную подстроку", true, false);
        commands[11] = new ExecuteScriptCommand("execute_script", "des", true, false);
        commands[12] = new HelpCommand("help", "Вывести справку по доступным командам", false, false);
        commands[13] = new HistoryCommand("history", "Вывести последние использованные команды", false, false);
        commands[14] = new SumOfMetersAboveSeaLevelCommand("sum_of_meters_above_sea_level",
                "Вывести сумму значений поля metersAboveSeaLevel для всех элементов коллекции",
                false, false);
        return commands;
    }
}
