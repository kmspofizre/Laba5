package collections;

import components.City;
import exceptions.FileTroubleException;
import readers.ReaderFromCSV;
import validators.CityCollectionValidator;

import java.io.IOException;
import java.util.*;

public class CSVDataBase extends DataBase{
    public CSVDataBase(String file_name){
        List<String []> validatedData = getValidatedData(getData(file_name));

        // создать новый метод, который из validatedData будет делать коллекцию, где ключи - id, а
        // значения - объекты класса City
    }
    // разбор параметров из csv
    @Override
    public List<String []> getData(String file_name){
        try {
            List<String []> parsed = ReaderFromCSV.readFromCSV(file_name);
            return parsed;
        }
        catch (IOException e){
            System.out.println("Не удалось найти файл");
        }
        catch (FileTroubleException exc){
            System.out.println(exc.getMessage());
        }
        return null;
    }
    public List<Long> getIds(List<String []> data){
        List<Long> idsList = new ArrayList<>();
        for (String [] item: data){
            idsList.add(Long.parseLong(item[0]));
        }
        System.out.println(idsList.toString());
        return idsList;
    }

    // validateData boolean
    // циклом перебирается parsed и на каждом шаге добавляется/не добавляется новый элемент в коллекцию
    // значения не прошли валидацию (вывести значения + вывести, какие конкретно данные не прошли валидацию)
    public List<String []>getValidatedData(List<String []> dataToValidate){
        List<Long> ids = getIds(dataToValidate);
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String []> validatedData = new ArrayList<>();
        long id = 1;
        long stringCounter = 1;
        for (String [] item : dataToValidate){
            System.out.println();
            System.out.println("Строка " + stringCounter + ":");
            if (cityValidator.validateData(item)){
                String [] merged = dataPreparer(id, item);
                validatedData.add(merged);
                System.out.println("Успешно");
                id++;
            }
            stringCounter++;
        }
        return validatedData;
    }
    public String [] dataPreparer(long id, String[] item){
        String [] merged = new String[12];
        merged[0] = String.valueOf(id);
        merged[1] = item[0];
        merged[2] = item[1];
        merged[3] = item[2];
        merged[4] = new Date().toString();
        merged[5] = item[3];
        merged[6] = item[4];
        merged[7] = item[5];
        merged[8] = item[6];
        merged[9] = item[7];
        merged[10] = item[8];
        merged[11] = item[9];
        return merged;
    }
}
