package utils;

import collections.CSVDataBase;
import commands.CommandHandler;

import java.util.Arrays;
import java.util.Scanner;

public class ProgramRunner {
    public void runProgram(CSVDataBase csvDataBase, CommandHandler commandHandler){
        Scanner scanner = new Scanner(System.in);
        InstructionFetcher infetch = new InstructionFetcher(commandHandler.getCommands());
        while (true){
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            // метод, который перебирает все виды команд и сравнивает с переданной
            // в instruction fetcher
            // если команда короткая, то провалидировать одно значение
            // если длинная - построчный ввод
            // проверить наличие inline аргументов
            if (infetch.instructionFetch(command[0],Arrays.copyOfRange(command,
                    1, command.length)).isMultiLines()){
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
