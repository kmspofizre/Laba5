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

public class CSVDataBase extends DataBase {
    private TreeMap<Long, City> dataBase;
    private Date initDate;
    private long lastCityId;
    private String fileName;

    public CSVDataBase(String file_name) {
        List<String[]> validatedData = getValidatedData(getData(file_name));
        TreeMap<Long, City> cityCollection = CityCollectionMaker.makeCityCollection(validatedData);
        this.initDate = new Date();
        this.dataBase = cityCollection;
        this.fileName = file_name;
    }

    @Override
    public List<String[]> getData(String file_name) {
        try {
            List<String[]> parsed = ReaderFromCSV.readFromCSV(file_name);
            return parsed;
        } catch (IOException e) {
            System.out.println("Не удалось найти файл");
        } catch (FileTroubleException exc) {
            System.out.println(exc.getMessage());
        } catch (IndexOutOfBoundsException excc) {
            System.out.println("Файл с БД пуст");
        }
        return null;
    }

    public List<Long> getIds(List<String[]> data) {
        List<Long> idsList = new ArrayList<>();
        for (String[] item : data) {
            idsList.add(Long.parseLong(item[0]));
        }
        System.out.println(idsList.toString());
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
            System.out.println();
            System.out.println("Строка " + stringCounter + ":");
            if (cityValidator.validateData(item)) {
                Long id = Long.parseLong(item[0]);
                String[] merged = dataPreparer(id, item);
                validatedData.add(merged);
                System.out.println("Успешно");
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
        System.out.println("Тип коллекции: TreeMap");
        System.out.println("Количество элементов: " + this.dataBase.size());
        System.out.println("Дата инициализации: " + this.initDate);
    }

    public void show() {
        for (Map.Entry<Long, City> item : this.dataBase.entrySet()) {
            System.out.println(item.getValue().toString());
        }
    }

    public void insert(long id, List<String[]> data, boolean fromScript) {
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
                    System.out.println("Элемент добавлен успешно");
                }
            } else {
                String[] merged = dataPreparer(id, data.get(0));
                validatedData.add(merged);
                TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
                this.dataBase.putAll(cityInstance);
                System.out.println("Элемент добавлен успешно");
            }

        } else {
            System.out.println("Элемент с таким id уже существует");
        }
    }

    public void update(long id, List<String[]> data, boolean fromScript) {
        CityCollectionValidator cityValidator = new CityCollectionValidator();
        List<String[]> validatedData = new ArrayList<>();
        if (this.dataBase.containsKey(id)) {
            if (fromScript) {
                if (cityValidator.validateData(data.get(0))) {
                    String[] merged = dataPreparer(id, data.get(0));
                    validatedData.add(merged);
                    TreeMap<Long, City> cityInstance = CityCollectionMaker.makeCityCollection(validatedData);
                    this.dataBase.putAll(cityInstance);
                    System.out.println("Элемент обновлен успешно");
                }
            } else {
                String[] merged = dataPreparer(id, data.get(0));
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

        public void remove ( long id){
            if (this.dataBase.containsKey(id)) {
                this.dataBase.remove(id);
                System.out.println("Элемент удален успешно");
            } else {
                System.out.println("Элемента с таким ключом не существует");
            }
        }
        public void clear () {
            this.dataBase.clear();
        }


        public void removeGreaterKey ( long id){
            for (Long currentId : this.dataBase.keySet()) {
                if (currentId > id) {
                    this.dataBase.remove(currentId);
                }
            }
            System.out.println("Элементы с ключами, большими, чем заданный удалены успешно");
        }

        public void sumOfMetersAboveSeaLevel () {
            Collection<City> values = this.dataBase.values();
            Double sum = 0.0;
            for (City item : values) {
                sum += item.getMetersAboveSeaLevel();
            }
            System.out.println("Сумма значений 'Высота над уровнем моря': " + sum);
        }

        public void countGreaterThanMetersAboveSeaLevel (Double metersAboveSeaLevel){
            Collection<City> values = this.dataBase.values();
            long count = 0;
            for (City item : values) {
                if (item.getMetersAboveSeaLevel() > metersAboveSeaLevel) {
                    count += 1;
                }
            }
            System.out.println("Количество городов, высота которых над уровнем море больше заданной: " + count);
        }

        public void filterContainsName (String name){
            Collection<City> values = this.dataBase.values();
            for (City item : values) {
                if (item.getName().contains(name)) {
                    System.out.println(item);
                }
            }
        }
        public void save () {
            try {
                CSVWriter.writeCityCollectionToCSV(this.fileName, this.dataBase);
            } catch (IOException e) {
                System.out.println("Не удалось найти файл");
            }
        }

        public void removeLower (Long id){
            if (this.dataBase.containsKey(id)) {
                TreeMap<Long, City> newCityCollection = new TreeMap<>();
                City city = this.dataBase.get(id);
                for (Map.Entry<Long, City> item : this.dataBase.entrySet()) {
                    if (item.getValue().compareTo(city) >= 0) {
                        newCityCollection.put(item.getKey(), item.getValue());
                    }
                }
                this.dataBase = newCityCollection;
            } else {
                System.out.println("Элемента с заданным ключом не существует");
            }

        }
    }
