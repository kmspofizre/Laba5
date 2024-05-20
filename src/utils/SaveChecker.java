package utils;


import collections.PostgresDataBase;
import components.City;
import validators.InputDataValidator;

import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

public class SaveChecker {
    public static boolean checkForSaves(PostgresDataBase dataBase){
        if (!(dataBase.compareWithTMP())){
            ResponseMachine.makeStringResponse("У вас есть несохраненные изменения с прошлой сессии");
            TMPManager tmpManager = dataBase.getTmpManager();
            TreeMap<Long, City> tmpCollection = tmpManager.getCollectionFromCSV();
            for (City item : tmpCollection.values()){
                ResponseMachine.makeStringResponse(item);
            }
            return agreeWithChanges();
        }
        return false;
    }
    public static boolean agreeWithChanges(){
        return InputDataValidator.yesOrNo("Хотите ли вы применить несохраненные изменения? (YES/NO)");
    }
}
