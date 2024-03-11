package collections;

import components.City;
import components.Climate;
import exceptions.FileTroubleException;
import readers.ReaderFromCSV;
import utils.CityCollectionMaker;
import validators.CityCollectionValidator;
import writers.CSVWriter;

import java.io.IOException;
import java.io.SyncFailedException;
import java.util.*;

public class CSVDataBase extends DataBase{
    private TreeMap<Long, City> dataBase;
    private Date initDate;
    private long lastCityId;
    private String fileName;
    public CSVDataBase(String file_name){
        List<String []> validatedData = getValidatedData(getData(file_name));
        TreeMap<Long, City> cityCollection = CityCollectionMaker.makeCityCollection(validatedData);
        //for (Map.Entry<Long, City> item : cityCollection.entrySet()){
            //System.out.println(item.getKey());
        //}
        this.initDate = new Date();
        this.dataBase = cityCollection;
        this.fileName = file_name;
        // создать новый метод, который из validatedData будет делать коллекцию, где ключи - id, а
        // значения - объекты класса City
    }
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
    // значения не прошли валидацию (вывести значения + вывести, какие конкретно данные не прошли валидацию)
    public List<String []>getValidatedData(List<String []> dataToValidate){
        if (dataToValidate == null){
            return new ArrayList<>();
        }
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
        this.lastCityId = id;
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
    public void info(){
        System.out.println("Тип коллекции: TreeMap");
        System.out.println("Количество элементов: " + this.dataBase.size());
        System.out.println("Дата инициализации: " + this.initDate);
    }

    public void show(){
        for (Map.Entry<Long, City> item : this.dataBase.entrySet()){
            System.out.println(item.getValue().toString());
        }
    }

    public void insert(List<String []> data){
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String []> validatedData = new ArrayList<>();
        if (cityValidator.validateData(data.get(0))){
            this.lastCityId = this.lastCityId + 1;
            String [] merged = dataPreparer(this.lastCityId, data.get(0));
            validatedData.add(merged);
            TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
            this.dataBase.putAll(cityInstance);
            System.out.println("Элемент добавлен успешно");
        }
    }

    public void update(long id, List<String []> data){
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String []> validatedData = new ArrayList<>();
        if (this.dataBase.containsKey(id)){
            if (cityValidator.validateData(data.get(0))){
                String [] merged = dataPreparer(id, data.get(0));
                validatedData.add(merged);
                TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
                this.dataBase.putAll(cityInstance);
                System.out.println("Элемент обновлен успешно");
            }
        }
        else {
            System.out.println("Элемент с заданным ключом не найден");
        }
    }

    public void remove(long id){
        if (this.dataBase.containsKey(id)){
            this.dataBase.remove(id);
            System.out.println("Элемент удален успешно");
        }
        else {
            System.out.println("Элемента с таким ключом не существует");
        }
    }
    public void clear(){
        this.dataBase.clear();
    }


    public void removeGreaterKey(long id){
        if (this.dataBase.containsKey(id)){
            this.dataBase = (TreeMap<Long, City>) this.dataBase.headMap(id);
            System.out.println("Элементы, большие, чем заданный удалены успешно");
        }
        else {
            System.out.println("Элемента с таким ключом не существует");
        }
    }

    public void sumOfMetersAboveSeaLevel(){
        Collection<City> values = this.dataBase.values();
        Double sum = 0.0;
        for (City item : values){
            sum += item.getMetersAboveSeaLevel();
        }
        System.out.println("Сумма значений 'Высота над уровнем моря': " + sum);
    }

    public void countGreaterThanMetersAboveSeaLevel(Double metersAboveSeaLevel){
        Collection<City> values = this.dataBase.values();
        long count = 0;
        for (City item : values){
            if (item.getMetersAboveSeaLevel() > metersAboveSeaLevel){
                count += 1;
            }
        }
        System.out.println("Количество городов, высота которых над уровнем море больше заданной: " + count);
    }

    public void filterContainsName(String name){
        Collection<City> values = this.dataBase.values();
        for (City item : values){
            if (item.getName().contains(name)){
                System.out.println(item);
            }
        }
    }
    public void save(){
        try {
            CSVWriter.writeCityCollectionToCSV(this.fileName, this.dataBase);
        }
        catch (IOException e){
            System.out.println("Не удалось найти файл");
        }
    }

    public void removeLower(Long id){
        TreeMap<Long, City> newCityCollection = new TreeMap<>();
        City city = this.dataBase.get(id);
        for (Map.Entry<Long, City> item : this.dataBase.entrySet()){
            if (item.getValue().compareTo(city) >= 0){
                newCityCollection.put(item.getKey(), item.getValue());
            }
        }
    }
}
