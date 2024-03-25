package utils;

import components.City;
import exceptions.FileTroubleException;
import readers.ReaderFromCSV;
import validators.CityCollectionValidator;
import writers.CSVWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class TMPManager {
    public void writeToTMP(TreeMap<Long, City> dataToWrite){
        try{
            CSVWriter.writeCityCollectionToCSV("tmp.csv", dataToWrite);
        }
        catch (IOException e){
            System.out.println("Файл для записи в tmp не найден");
        }
    }

    public TreeMap<Long, City> getCollectionFromCSV(){
        List<String[]> validatedData = getValidatedData(getData("tmp.csv"));
        TreeMap<Long, City> cityCollection = CityCollectionMaker.makeCityCollection(validatedData);
        return cityCollection;
    }

    public List<String[]> getData(String file_name) {
        try {
            List<String[]> parsed = ReaderFromCSV.readFromCSV(file_name);
            return parsed;
        } catch (IOException e) {
            System.out.println("Не удалось найти файл");
        } catch (FileTroubleException exc) {
            System.out.println(exc.getMessage());
        } catch (IndexOutOfBoundsException excc) {
            System.out.print("");
        }
        return null;
    }

    public List<String[]> getValidatedData(List<String[]> dataToValidate) {
        if (dataToValidate == null) {
            return new ArrayList<>();
        }
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String[]> validatedData = new ArrayList<>();
        long stringCounter = 1;
        for (String[] item : dataToValidate) {
            if (cityValidator.validateData(item)) {
                Long id = Long.parseLong(item[0]);
                String[] merged = dataPreparer(id, item);
                validatedData.add(merged);
            }
            stringCounter++;
        }
        return validatedData;
    }

    public String[] dataPreparer(long id, String[] item) {
        String[] merged = new String[12];
        merged[0] = String.valueOf(id);
        merged[1] = item[1];
        merged[2] = item[2];
        merged[3] = item[3];
        merged[4] = new Date().toString();
        merged[5] = item[4];
        merged[6] = item[5];
        merged[7] = item[6];
        merged[8] = item[7];
        merged[9] = item[8];
        merged[10] = item[9];
        merged[11] = item[10];
        return merged;
    }
}
