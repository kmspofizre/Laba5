package utils;

import collections.CSVDataBase;
import commands.CommandHandler;

import java.util.Scanner;

public class ProgramRunner {
    public void runProgram(CSVDataBase csvDataBase, CommandHandler commandHandler){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String line = scanner.nextLine();
            String[] command = line.split(" ");
        }
    }
}
