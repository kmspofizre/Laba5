import collections.CSVDataBase;
import commands.Command;
import commands.CommandHandler;
import utils.CommandsInitiator;
import utils.ProgramRunner;


public class Main {
    public static void main(String[] args) {
        CSVDataBase dataBase = new CSVDataBase("test_this_crap.csv");
        Command [] commands = CommandsInitiator.initCommands();

        CommandHandler handler = new CommandHandler(dataBase, commands);
        ProgramRunner programRunner = new ProgramRunner(dataBase, handler);
        programRunner.runProgram();


        // добавить уточнение при удалении коллекции
        // класс для чтения из файлов/бд
        // класс для получения инфы из командной строки
    }
}
