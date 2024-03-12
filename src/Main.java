import collections.CSVDataBase;
import commands.Command;
import commands.CommandHandler;


public class Main {
    public static void main(String[] args) {
        // запускать программу с ключом, чтобы убрать статус сбора коллекции
        CSVDataBase dataBase = new CSVDataBase("test_this_crap.csv");
        // init commands
        Command [] commands = new Command[12];
        CommandHandler handler = new CommandHandler(dataBase, commands);
        // добавить уточнение при удалении коллекции
        // класс для чтения из файлов/бд
        // класс для получения инфы из командной строки
    }
}
