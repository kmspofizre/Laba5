import collections.CSVDataBase;
import commands.Command;
import commands.CommandHandler;
import utils.CommandsInitiator;
import utils.ProgramRunner;
import utils.SaveChecker;


public class Main {
    public static void main(String[] args) {
        CSVDataBase dataBase = new CSVDataBase("test_this_crap.csv");

        Command [] commands = CommandsInitiator.initCommands();

        CommandHandler handler = new CommandHandler(dataBase, commands);
        ProgramRunner programRunner = new ProgramRunner(dataBase, handler);
        if (SaveChecker.checkForSaves(dataBase)){
            dataBase.getDataFromTMP();
            System.out.println("Загружены данные с последнего сохранения");
        }
        else {
            dataBase.writeCollectionToTMP();
        }
        programRunner.runProgram();
    }
}
