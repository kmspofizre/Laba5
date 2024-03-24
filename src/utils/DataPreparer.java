package utils;

import commands.Command;
import exceptions.CommandExecutingException;

import java.util.Scanner;

public class DataPreparer {
    public static String[] prepareData(Command command, String[] inlineArgs,
                                       Scanner scanner) throws CommandExecutingException, WrongThreadException,
            NumberFormatException{
        if (command.isHasInlineArguments()){
            if (inlineArgs.length == 0){
                throw new CommandExecutingException("Inline аргументы не были доставлены");
            }
        }
        String [] data = command.prepareData(inlineArgs, scanner);
        return data;
    }
}
