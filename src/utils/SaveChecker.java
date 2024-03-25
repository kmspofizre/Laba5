package utils;

import collections.CSVDataBase;

import java.util.Objects;
import java.util.Scanner;

public class SaveChecker {
    public static boolean checkForSaves(CSVDataBase dataBase){
        if (!dataBase.compareWithTMP()){
            return agreeWithChanges();
        }
        return false;
    }
    public static boolean agreeWithChanges(){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (!Objects.equals(line, "YES") | !Objects.equals(line, "NO")){
            System.out.println("Хотите ли вы применить несохраненные изменения? (YES/NO)");
        }
        if (line.equals("YES")){
            return true;
        }
        else if (line.equals("NO")){
            return false;
        }
        return false;
    }
}
