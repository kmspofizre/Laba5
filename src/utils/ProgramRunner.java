package utils;

import collections.CSVDataBase;
import commands.Command;
import commands.CommandHandler;
import exceptions.CommandExecutingException;

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
            try {
                Command currentCommand = infetch.instructionFetch(command[0]);
                // добавить проверку на наследника CHC (команды для CommandHandler) через isAssignableFrom
                if (currentCommand.isMultiLines()){
                    // собрать данные и передать в CH
                    // данные собирать с помощью DataPreparer
                    if (currentCommand.isHasInlineArguments()){
                       // передаем в DataPreparer inline значение, его валидируем на стадии execute
                        // остальные значения вводятся через построчный ввод при помощи специального метода у каждой команды
                    }
                    else {
                        // если нет inline аргументов, то построчный ввод при помощи специального метода у команды
                    }
                }
                else{
                    // проверить inline значения в DataPreparer с помощью валидатора
                    // отправить в CH Inline значение
                    // данные собирать с помощью DataPreparer
                }
            }
            catch (CommandExecutingException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
