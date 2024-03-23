package utils;

import collections.CSVDataBase;
import commands.Command;
import commands.CommandHandler;

import java.util.Arrays;
import java.util.Scanner;

public class ProgramRunner {
    // fromScript
    public void runProgram(CSVDataBase csvDataBase, CommandHandler commandHandler){
        Scanner scanner = new Scanner(System.in);
        InstructionFetcher infetch = new InstructionFetcher(commandHandler.getCommands());
        while (true){
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            // если команда короткая, то провалидировать одно значение
            // если длинная - построчный ввод
            // проверить наличие inline аргументов
            Command currentCommand = infetch.instructionFetch(command[0]);
            if (currentCommand.isMultiLines()){
                // собрать данные и передать в CH
                // данные собирать с помощью DataPreparer
            }
            else{

                // Отправить в CH Inline значение
                // данные собирать с помощью DataPreparer
            }
        }
    }
}
