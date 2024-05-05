package collections;

import components.City;
import components.Climate;
import components.DataBaseResponse;
import components.Response;
import exceptions.CommandExecutingException;
import exceptions.FileTroubleException;
import readers.ReaderFromCSV;
import utils.CityCollectionMaker;
import utils.ResponseMachine;
import utils.TMPManager;
import validators.CityCollectionValidator;
import validators.InputDataValidator;
import writers.CSVWriter;

import java.io.IOException;
import java.io.SyncFailedException;
import java.util.*;

public class CSVDataBase extends DataBase {
    private TreeMap<Long, City> dataBase;
    private Date initDate;
    private long lastCityId;
    private String fileName;
    private TMPManager tmpManager;

    public CSVDataBase(String file_name) {
        List<String[]> validatedData = getValidatedData(getData(file_name));
        TreeMap<Long, City> cityCollection = CityCollectionMaker.makeCityCollection(validatedData);
        this.initDate = new Date();
        this.dataBase = cityCollection;
        this.fileName = file_name;
        this.tmpManager = new TMPManager();
    }
    public boolean compareWithTMP(){
        TreeMap<Long, City> tmpCollection = this.tmpManager.getCollectionFromCSV();
        if (this.tmpManager.getCollectionFromCSV().isEmpty()){
            return true;
        }
        return (this.dataBase.equals(tmpCollection));
    }

    public void getDataFromTMP(){
        this.dataBase = this.tmpManager.getCollectionFromCSV();
        save();
    }

    public void writeCollectionToTMP(){
        this.tmpManager.writeToTMP(this.dataBase);
    }

    @Override
    public List<String[]> getData(String file_name) {
        try {
            List<String[]> parsed = ReaderFromCSV.readFromCSV(file_name);
            return parsed;
        } catch (IOException e) {
            ResponseMachine.makeStringResponse("Не удалось найти файл");
        } catch (FileTroubleException exc) {
            ResponseMachine.makeStringResponse(exc.getMessage());
        } catch (IndexOutOfBoundsException excc) {
            ResponseMachine.makeStringResponse("Файл с БД пуст");
        }
        return null;
    }

    public List<Long> getIds(List<String[]> data) {
        List<Long> idsList = new ArrayList<>();
        for (String[] item : data) {
            idsList.add(Long.parseLong(item[0]));
        }
        ResponseMachine.makeStringResponse(idsList.toString());
        return idsList;
    }

    public List<String[]> getValidatedData(List<String[]> dataToValidate) {
        if (dataToValidate == null) {
            return new ArrayList<>();
        }
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String[]> validatedData = new ArrayList<>();
        long stringCounter = 1;
        for (String[] item : dataToValidate) {
            ResponseMachine.makeStringResponse("");
            ResponseMachine.makeStringResponse("Строка " + stringCounter + ":");
            if (cityValidator.validateData(item)) {
                Long id = Long.parseLong(item[0]);
                String[] merged = dataPreparer(id, item);
                validatedData.add(merged);
                ResponseMachine.makeStringResponse("Успешно");
            }
            stringCounter++;
        }
        return validatedData;
    }

    public String[] dataPreparer(long id, String[] item) {
        String[] merged = new String[11];
        merged[0] = String.valueOf(id);
        merged[1] = item[1];
        merged[2] = item[2];
        merged[3] = item[3];
        merged[4] = item[4];
        merged[5] = item[5];
        merged[6] = item[6];
        merged[7] = item[7];
        merged[8] = item[8];
        merged[9] = item[9];
        merged[10] = item[10];
        return merged;
    }

    public Response info() {
        String resp = "Тип коллекции: TreeMap\nКоличество элементов: " +
                this.dataBase.size() + "\nДата инициализации: " + this.initDate;
        return ResponseMachine.makeClientResponse(resp);
    }

    public Response show() {
        String resp = "";
        for (Map.Entry<Long, City> item : this.dataBase.entrySet()) {
            resp = resp + item.getValue().toString() + "\n";
        }
        return ResponseMachine.makeClientResponse(resp);
    }

    public Response insert(City city) throws CommandExecutingException {
        if (!this.dataBase.containsKey(city.getId())) {
            DataBaseResponse dbResponse = new DataBaseResponse("Элемент добавлен успешно");
            TreeMap<Long, City> backup = new TreeMap<>();
            backup.put(city.getId(), city);
            dbResponse.addDeletedPart(backup);
            this.dataBase.put(city.getId(), city);
            dbResponse.setSuccess(true);
            return dbResponse;
        } else {
            DataBaseResponse dbResponse = new DataBaseResponse("Элемент с таким id уже существует");
            dbResponse.setSuccess(true);
            return dbResponse;
        }
    }

    public Response update(City city) throws CommandExecutingException {
        if (this.dataBase.containsKey(city.getId())) {
            this.dataBase.put(city.getId(), city);
            DataBaseResponse dbResponse = new DataBaseResponse("Элемент обновлен успешно");
            TreeMap<Long, City> backup = new TreeMap<>();
            backup.put(city.getId(), city);
            dbResponse.addDeletedPart(backup);
            dbResponse.setSuccess(true);
            return dbResponse;
        }
        else {
            DataBaseResponse dbResponse = new DataBaseResponse("Элемент с заданным ключом не найден");
            dbResponse.setSuccess(false);
            return dbResponse;
        }
    }

        public Response remove (long id, boolean fromScript){
            if (this.dataBase.containsKey(id)) {
                DataBaseResponse dbResponse = new DataBaseResponse("Элемент добавлен успешно");
                TreeMap<Long, City> backup = new TreeMap<>();
                City city = this.dataBase.get(id);
                backup.put(id, city);
                dbResponse.addDeletedPart(backup);
                this.dataBase.remove(id);
                dbResponse.setSuccess(true);
                return dbResponse;

            } else {
                DataBaseResponse dbResponse = new DataBaseResponse("Элемента с таким ключом не существует");
                dbResponse.setSuccess(false);
                return dbResponse;
            }
        }
        public Response clear () {
            DataBaseResponse dbResponse = new DataBaseResponse("Коллекция очищена");
            TreeMap<Long, City> backup = (TreeMap<Long, City>) this.dataBase.clone();
            dbResponse.addDeletedPart(backup);
            this.dataBase.clear();
            dbResponse.setSuccess(true);
            return dbResponse;
        }


        public Response removeGreaterKey (long id, boolean fromScript){
            DataBaseResponse dbResponse = new DataBaseResponse("Элементы с ключами, большими, чем заданный удалены успешно");
            TreeMap<Long, City> backup = new TreeMap<>();
            TreeMap<Long, City> copy = (TreeMap<Long, City>) this.dataBase.clone();
            for (Long currentId : copy.keySet()) {
                if (currentId > id) {
                    backup.put(id, this.dataBase.get(id));
                    this.dataBase.remove(currentId);
                }
            }
            dbResponse.addDeletedPart(backup);
            dbResponse.setSuccess(true);
            return dbResponse;
        }

        public Response sumOfMetersAboveSeaLevel () {
            Collection<City> values = this.dataBase.values();
            Double sum = 0.0;
            for (City item : values) {
                sum += item.getMetersAboveSeaLevel();
            }
            return ResponseMachine.makeClientResponse("Сумма значений 'Высота над уровнем моря': " + sum);
        }

        public Response countGreaterThanMetersAboveSeaLevel (Double metersAboveSeaLevel){
            Collection<City> values = this.dataBase.values();
            long count = 0;
            for (City item : values) {
                if (item.getMetersAboveSeaLevel() > metersAboveSeaLevel) {
                    count += 1;
                }
            }
            return ResponseMachine.makeClientResponse("Количество городов, высота которых над уровнем море больше заданной: " + count);
        }

        public Response filterContainsName (String name){
            Collection<City> values = this.dataBase.values();
            String resp;
            resp = "Элементы, содержащие " + name + "\n";

            for (City item : values) {
                if (item.getName().contains(name)) {
                    resp = resp + item + "\n";
                }
            }
            return ResponseMachine.makeClientResponse(resp);
        }
        public Response save () {
            try {
                CSVWriter.writeCityCollectionToCSV(this.fileName, this.dataBase);
                return ResponseMachine.makeClientResponse("Данные сохранены");
            } catch (IOException e) {
                return ResponseMachine.makeClientResponse("Не удалось найти файл");
            }
        }

        public Response removeLower (Long id, boolean fromScript){
            DataBaseResponse dbResponse = new DataBaseResponse("Элементы удалены");
            if (this.dataBase.containsKey(id)) {
                TreeMap<Long, City> backup = new TreeMap<>();
                TreeMap<Long, City> newCityCollection = new TreeMap<>();
                City city = this.dataBase.get(id);
                for (Map.Entry<Long, City> item : this.dataBase.entrySet()) {
                    if (item.getValue().compareTo(city) >= 0) {
                        newCityCollection.put(item.getKey(), item.getValue());
                    }
                    else {
                        backup.put(item.getKey(), item.getValue());
                    }
                }
                this.dataBase = newCityCollection;
                dbResponse.addDeletedPart(backup);
                dbResponse.setSuccess(true);
                return dbResponse;

            } else {
                dbResponse.setResponseString("Элемента с заданным ключом не существует");
                dbResponse.setSuccess(false);
                return ResponseMachine.makeClientResponse("Элемента с заданным ключом не существует");
            }
        }

    public TMPManager getTmpManager() {
        return tmpManager;
    }

    public void insertAllFromCollection(TreeMap<Long, City> toInsert){
        this.dataBase.putAll(toInsert);
    }
    public Response removeAllFromCollection(TreeMap<Long, City> toRemove){
        for (Map.Entry<Long, City> entry : toRemove.entrySet()){
            if (this.dataBase.containsKey(entry.getKey())){
                this.dataBase.remove(entry.getKey());
                return new Response("Добавление элемента в коллекцию удалено");
            }
            else {
                return new Response("Элемент уже удален");
            }
        }
        return null;
    }
}
