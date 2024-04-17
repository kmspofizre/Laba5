package collections;

import components.City;
import components.Climate;
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

    public void info() {
        ResponseMachine.makeStringResponse("Тип коллекции: TreeMap");
        ResponseMachine.makeStringResponse("Количество элементов: " + this.dataBase.size());
        ResponseMachine.makeStringResponse("Дата инициализации: " + this.initDate);
    }

    public void show() {
        for (Map.Entry<Long, City> item : this.dataBase.entrySet()) {
            ResponseMachine.makeStringResponse(item.getValue().toString());
        }
    }

    public void insert(long id, List<String[]> data, boolean fromScript) throws CommandExecutingException {
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String[]> validatedData = new ArrayList<>();
        if (!this.dataBase.containsKey(id)) {
            if (fromScript) {
                if (cityValidator.validateData(data.get(0))) {
                    this.lastCityId = this.lastCityId + 1;
                    String[] merged = dataPreparer(id, data.get(0));
                    validatedData.add(merged);
                    TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
                    this.dataBase.putAll(cityInstance);
                    ResponseMachine.makeStringResponse("Элемент добавлен успешно");
                }
            } else {
                String[] merged = dataPreparer(id, data.get(0));
                validatedData.add(merged);
                TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
                this.dataBase.putAll(cityInstance);
                ResponseMachine.makeStringResponse("Элемент добавлен успешно");
                writeCollectionToTMP();
            }

        } else {
            throw new CommandExecutingException("Элемент с таким id уже существует");
        }
    }

    public void update(long id, List<String[]> data, boolean fromScript) throws CommandExecutingException {
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String[]> validatedData = new ArrayList<>();
        if (this.dataBase.containsKey(id)) {
            if (fromScript) {
                if (cityValidator.validateData(data.get(0))) {
                    String[] merged = dataPreparer(id, data.get(0));
                    validatedData.add(merged);
                    TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
                    this.dataBase.putAll(cityInstance);
                    ResponseMachine.makeStringResponse("Элемент обновлен успешно");
                }
            } else {
                String[] merged = dataPreparer(id, data.get(0));
                validatedData.add(merged);
                TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
                this.dataBase.putAll(cityInstance);
                ResponseMachine.makeStringResponse("Элемент обновлен успешно");
                writeCollectionToTMP();
            }
        }
        else {
            throw new CommandExecutingException("Элемент с заданным ключом не найден");
        }
    }

        public void remove (long id, boolean fromScript){
            if (this.dataBase.containsKey(id)) {
                this.dataBase.remove(id);
                ResponseMachine.makeStringResponse("Элемент удален успешно");
                if (!fromScript){
                    writeCollectionToTMP();
                }
            } else {
                ResponseMachine.makeStringResponse("Элемента с таким ключом не существует");
            }
        }
        public void clear () {
        if (InputDataValidator.yesOrNo("Вы уверены, что хотите очистить коллекцию? (YES/NO)")){
            this.dataBase.clear();
            ResponseMachine.makeStringResponse("Коллекция очищена");
        }
        }


        public void removeGreaterKey (long id, boolean fromScript){
            for (Long currentId : this.dataBase.keySet()) {
                if (currentId > id) {
                    this.dataBase.remove(currentId);
                    if (!fromScript){
                        writeCollectionToTMP();
                    }
                }
            }
            ResponseMachine.makeStringResponse("Элементы с ключами, большими, чем заданный удалены успешно");
        }

        public void sumOfMetersAboveSeaLevel () {
            Collection<City> values = this.dataBase.values();
            Double sum = 0.0;
            for (City item : values) {
                sum += item.getMetersAboveSeaLevel();
            }
            ResponseMachine.makeStringResponse("Сумма значений 'Высота над уровнем моря': " + sum);
        }

        public void countGreaterThanMetersAboveSeaLevel (Double metersAboveSeaLevel){
            Collection<City> values = this.dataBase.values();
            long count = 0;
            for (City item : values) {
                if (item.getMetersAboveSeaLevel() > metersAboveSeaLevel) {
                    count += 1;
                }
            }
            ResponseMachine.makeStringResponse("Количество городов, высота которых над уровнем море больше заданной: " + count);
        }

        public void filterContainsName (String name){
            Collection<City> values = this.dataBase.values();
            ResponseMachine.makeStringResponse("Элементы, содержащие " + name);
            for (City item : values) {
                if (item.getName().contains(name)) {
                    ResponseMachine.makeStringResponse(item);
                }
            }
        }
        public void save () {
            try {
                CSVWriter.writeCityCollectionToCSV(this.fileName, this.dataBase);
                ResponseMachine.makeStringResponse("Данные сохранены");
            } catch (IOException e) {
                ResponseMachine.makeStringResponse("Не удалось найти файл");
            }
        }

        public void removeLower (Long id, boolean fromScript){
            if (this.dataBase.containsKey(id)) {
                TreeMap<Long, City> newCityCollection = new TreeMap<>();
                City city = this.dataBase.get(id);
                for (Map.Entry<Long, City> item : this.dataBase.entrySet()) {
                    if (item.getValue().compareTo(city) >= 0) {
                        newCityCollection.put(item.getKey(), item.getValue());
                    }
                }
                this.dataBase = newCityCollection;
                if (!fromScript){
                    writeCollectionToTMP();
                    ResponseMachine.makeStringResponse("Элементы удалены");
                }
            } else {
                ResponseMachine.makeStringResponse("Элемента с заданным ключом не существует");
            }

        }

    public TMPManager getTmpManager() {
        return tmpManager;
    }
}
