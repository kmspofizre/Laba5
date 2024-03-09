package collections;

import components.City;
import exceptions.FileTroubleException;
import readers.ReaderFromCSV;
import validators.CityCollectionValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.TreeMap;

public class CSVDataBase extends DataBase{
    public CSVDataBase(String file_name){
        List<String []> validatedData = getValidatedData(getData(file_name));
        // создать новый метод, который из validatedData будет делать коллекцию, где ключи - id, а
        // значения - объекты класса City
        // Сделать срез полученного списка, где последний элемент - ключ
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
        long id = 1;

        for (String [] item : dataToValidate){
            System.out.println("Строка " + id + ":");
            if (cityValidator.validateData(item)){

                // написать, в какой строке файла ошибочные данные
                // создание айдишников при валидном item
                // генерировать дату
                System.out.println("Успешно");
                id++;
            }
        }
    }
}
