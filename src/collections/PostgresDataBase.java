package collections;

import components.*;
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
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

public class PostgresDataBase extends DataBase {
    private TreeMap<Long, City> dataBase;
    private Date initDate;
    private long lastCityId;
    private String fileName;
    private TMPManager tmpManager;
    private Connection connection;

    public PostgresDataBase(String file_name, Connection connection) {
        List<String[]> validatedData = getValidatedData(getData(file_name));
        TreeMap<Long, City> cityCollection = CityCollectionMaker.makeCityCollection(validatedData);
        this.initDate = new Date();
        this.dataBase = cityCollection;
        this.fileName = file_name;
        this.tmpManager = new TMPManager();
        this.connection = connection;
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

    public Response show(User user) throws SQLException {
        PreparedStatement showStatement = this.connection.prepareStatement("SELECT * FROM city");
        PreparedStatement coordinatesStatement = this.connection.prepareStatement("SELECT * FROM coordinates WHERE id = ?");
        PreparedStatement humanStatement = this.connection.prepareStatement("SELECT * FROM human WHERE id = ?");
        PreparedStatement climateStatement = this.connection.prepareStatement("SELECT * FROM climate WHERE id = ?");
        PreparedStatement governmentStatement = this.connection.prepareStatement("SELECT * FROM government WHERE id = ?");
        PreparedStatement standardOfLivingStatement = this.connection.prepareStatement("SELECT * FROM standard_of_living WHERE id = ?");
        PreparedStatement userStatement = this.connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        userStatement.setInt(1, user.getId());
        ResultSet userSet = userStatement.executeQuery();
        userSet.next();
        int userId = userSet.getInt("id");
        String resp = "";
        ResultSet resultSet = showStatement.executeQuery();
        String newCity;
        while (resultSet.next()){
            String name = resultSet.getString("name");
            Integer area = resultSet.getInt("area");
            Integer cityPopulation = resultSet.getInt("city_population");
            Double metersAboveSeaLevel = resultSet.getDouble("meters_above_sea_level");
            coordinatesStatement.setInt(1, resultSet.getInt("coordinate"));
            ResultSet coordinatesSet = coordinatesStatement.executeQuery();
            coordinatesSet.next();
            Float x = coordinatesSet.getFloat("x");
            Integer y = coordinatesSet.getInt("y");
            humanStatement.setInt(1, resultSet.getInt("governor"));
            ResultSet humanSet = humanStatement.executeQuery();
            humanSet.next();
            Integer governorAge = humanSet.getInt("age");
            climateStatement.setInt(1, resultSet.getInt("climate"));
            ResultSet climateSet = climateStatement.executeQuery();
            climateSet.next();
            String climateName = climateSet.getString("climate_name");
            governmentStatement.setInt(1, resultSet.getInt("government"));
            ResultSet governmentSet = governmentStatement.executeQuery();
            governmentSet.next();
            String governmentName = governmentSet.getString("government_name");
            // next
            standardOfLivingStatement.setInt(1, resultSet.getInt("standard_of_living"));
            standardOfLivingStatement.executeQuery();
            ResultSet standardSet = standardOfLivingStatement.executeQuery();
            standardSet.next();
            String standardName = standardSet.getString("standard_of_living_name");
            Boolean userOwner = (resultSet.getInt("user_id") == userId);
            newCity = name + "_" + x + "_" + y + "_" + area + "_" + cityPopulation + "_" + metersAboveSeaLevel + "_" + climateName + "_" + governmentName + "_" + standardName + "_" + governorAge + "_" + userOwner + "_" + userId;

            resp = resp + newCity + "\n";
        }
        return ResponseMachine.makeClientResponse(resp);
    }

    public Response insert(City city, User user) throws CommandExecutingException, SQLException {
        boolean cityExists = this.cityExists(city.getId());
        if (!cityExists) {
            long id = city.getId();
            String name = city.getName();
            Coordinates coordinates = city.getCoordinates();
            Date date = city.getCreationDate();
            int area = city.getArea();
            int population = city.getPopulation();
            Double metersAboveSeaLevel = city.getMetersAboveSeaLevel();
            Climate climate = city.getClimate();
            Government government = city.getGovernment();
            StandardOfLiving standardOfLiving = city.getStandardOfLiving();
            Human governor = city.getGovernor();
            int coordinatesId = getCoordsId(coordinates);
            int governorId = getGovernorId(governor);
            int standardOfLivingId = getStandardOfLivingId(standardOfLiving);
            int governmentId = getGovernmentId(government);
            int climateId = getClimateId(climate);
            int userId = user.getId();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement insertStatement = this.connection.prepareStatement("INSERT INTO city(id, name, coordinate, creation_date, " +
                    "area, city_population, meters_above_sea_level, climate, " +
                    "government, standard_of_living, governor, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStatement.setLong(1, id);
            insertStatement.setString(2, name);
            insertStatement.setInt(3, coordinatesId);
            insertStatement.setDate(4, sqlDate);
            insertStatement.setInt(5, area);
            insertStatement.setInt(6, population);
            insertStatement.setDouble(7, metersAboveSeaLevel);
            insertStatement.setInt(8, climateId);
            insertStatement.setInt(9, governmentId);
            insertStatement.setInt(10, standardOfLivingId);
            insertStatement.setInt(11, governorId);
            insertStatement.setInt(12, userId);
            insertStatement.executeUpdate();
            DataBaseResponse dbResponse = new DataBaseResponse("Элемент добавлен успешно");
            TreeMap<Long, City> backup = new TreeMap<>();
            backup.put(city.getId(), city);
            dbResponse.addDeletedPart(backup);
            this.dataBase.put(city.getId(), city);
            dbResponse.setSuccess(true);
            return dbResponse;
        }
        else {
            DataBaseResponse dbResponse = new DataBaseResponse("Элемент с таким id уже существует");
            dbResponse.setSuccess(true);
            return dbResponse;
        }
    }

    public Response update(City city, User user) throws CommandExecutingException, SQLException {
        boolean cityExists = this.cityExists(city.getId());
        boolean userOwner = userOwner(user, city.getId());
        if (cityExists & userOwner) {
            String name = city.getName();
            Coordinates coordinates = city.getCoordinates();
            Date date = city.getCreationDate();
            int area = city.getArea();
            int population = city.getPopulation();
            Double metersAboveSeaLevel = city.getMetersAboveSeaLevel();
            Climate climate = city.getClimate();
            Government government = city.getGovernment();
            StandardOfLiving standardOfLiving = city.getStandardOfLiving();
            Human governor = city.getGovernor();
            int coordinatesId = getCoordsId(coordinates);
            int governorId = getGovernorId(governor);
            int standardOfLivingId = getStandardOfLivingId(standardOfLiving);
            int governmentId = getGovernmentId(government);
            int climateId = getClimateId(climate);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement insertStatement = this.connection.prepareStatement("UPDATE city SET name = ?, coordinate = ?, creation_date = ?, " +
                    "area = ?, city_population = ?, meters_above_sea_level = ?, climate = ?, " +
                    "government = ?, standard_of_living = ?, governor = ?");
            insertStatement.setString(1, name);
            insertStatement.setInt(2, coordinatesId);
            insertStatement.setDate(3, sqlDate);
            insertStatement.setInt(4, area);
            insertStatement.setInt(5, population);
            insertStatement.setDouble(6, metersAboveSeaLevel);
            insertStatement.setInt(7, climateId);
            insertStatement.setInt(8, governmentId);
            insertStatement.setInt(9, standardOfLivingId);
            insertStatement.setInt(10, governorId);
            insertStatement.executeUpdate();


            DataBaseResponse dbResponse = new DataBaseResponse("Элемент обновлен успешно");
            TreeMap<Long, City> backup = new TreeMap<>();
            backup.put(city.getId(), this.dataBase.get(city.getId()));
            dbResponse.addDeletedPart(backup);
            this.dataBase.put(city.getId(), city);
            dbResponse.setSuccess(true);
            return dbResponse;
        }
        else {
            DataBaseResponse dbResponse = new DataBaseResponse("Элемент с заданным ключом не найден или вы не являетесь владельцем");
            dbResponse.setSuccess(false);
            return dbResponse;
        }
    }

        public Response remove(long id, User user) throws SQLException {
            boolean cityExists = this.cityExists(id);
            boolean isOwner = userOwner(user, id);
            if (cityExists & isOwner) {
                PreparedStatement removeStatement = this.connection.prepareStatement("DELETE FROM city WHERE id = ?");
                removeStatement.setLong(1, id);
                removeStatement.executeUpdate();
                DataBaseResponse dbResponse = new DataBaseResponse("Элемент удален успешно");
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
        public Response clear(User user) throws SQLException {
            PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM city WHERE user_id = ?");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
            DataBaseResponse dbResponse = new DataBaseResponse("Коллекция очищена");
            TreeMap<Long, City> backup = (TreeMap<Long, City>) this.dataBase.clone();
            dbResponse.addDeletedPart(backup);
            this.dataBase.clear();
            dbResponse.setSuccess(true);
            return dbResponse;
        }


        public Response removeGreaterKey (long id, User user) throws SQLException {
            DataBaseResponse dbResponse = new DataBaseResponse("Элементы с ключами, большими, чем заданный удалены успешно");
            TreeMap<Long, City> backup = new TreeMap<>();
            TreeMap<Long, City> copy = (TreeMap<Long, City>) this.dataBase.clone();
            for (Long currentId : copy.keySet()) {
                if (currentId > id) {
                    backup.put(currentId, this.dataBase.get(currentId));
                    this.dataBase.remove(currentId);
                }
            }
            PreparedStatement removeGreaterStatement = this.connection.prepareStatement("DELETE FROM city WHERE id < ? AND user_id = ?");
            removeGreaterStatement.setLong(1, id);
            removeGreaterStatement.setInt(2, user.getId());
            removeGreaterStatement.executeUpdate();
            dbResponse.addDeletedPart(backup);
            dbResponse.setSuccess(true);
            return dbResponse;
        }

        public Response sumOfMetersAboveSeaLevel() throws SQLException {
            Collection<City> values = this.dataBase.values();
            Double sum = 0.0;
            PreparedStatement sumOfMetersStatement = this.connection.prepareStatement("SELECT * FROM city");
            ResultSet resultSet = sumOfMetersStatement.executeQuery();
            while (resultSet.next()){
                sum += resultSet.getDouble("meters_above_sea_level");
            }
            return ResponseMachine.makeClientResponse("Сумма значений 'Высота над уровнем моря': " + sum);
        }

        public Response countGreaterThanMetersAboveSeaLevel (Double metersAboveSeaLevel) throws SQLException {
            long count = 0;
            PreparedStatement sumOfMetersStatement = this.connection.prepareStatement("SELECT * FROM city");
            ResultSet resultSet = sumOfMetersStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getDouble("meters_above_sea_level") > metersAboveSeaLevel) {
                    count += 1;
                }
            }
            return ResponseMachine.makeClientResponse("Количество городов, высота которых над уровнем море больше заданной: " + count);
        }

        public Response filterContainsName (String name) throws SQLException {
            Collection<City> values = this.dataBase.values();
            String resp;
            resp = "Элементы, содержащие " + name + "\n";
            PreparedStatement sumOfMetersStatement = this.connection.prepareStatement("SELECT * FROM city");
            ResultSet resultSet = sumOfMetersStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("name").contains(name)) {
                    resp = resp + resultSet.getString("name") + "\n";
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

        public Response removeLower (Long id, User user) throws SQLException {
            DataBaseResponse dbResponse = new DataBaseResponse("Элементы удалены");
            boolean cityExists = this.cityExists(id);
            if (cityExists) {
                PreparedStatement particularCity = this.connection.prepareStatement("SELECT * FROM city WHERE id = ? AND user_id = ?");

                particularCity.setLong(1, id);
                particularCity.setInt(2, user.getId());
                ResultSet oneMoreCity = particularCity.executeQuery();
                oneMoreCity.next();
                PreparedStatement allCitiesStatement = this.connection.prepareStatement("SELECT * FROM city");
                ResultSet resultSet = allCitiesStatement.executeQuery();
                TreeMap<Long, City> backup = new TreeMap<>();
                TreeMap<Long, City> newCityCollection = new TreeMap<>();
                City city = this.dataBase.get(id);
                while (resultSet.next()){
                    if (resultSet.getInt("city_population") < oneMoreCity.getInt("city_population")){
                        PreparedStatement deleteStatement = this.connection.prepareStatement("DELETE FROM city WHERE id = ?");
                        deleteStatement.setLong(1, resultSet.getLong("id"));
                        deleteStatement.executeUpdate();
                    }
                }
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
                return dbResponse;
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
                return new Response("Добавление элемента в коллекцию отменено");
            }
            else {
                return new Response("Элемент уже удален");
            }
        }
        return null;
    }


    public boolean cityExists(long id) throws SQLException {
        PreparedStatement cityExists = this.connection.prepareStatement("SELECT * FROM city WHERE id = ?");
        cityExists.setLong(1, id);
        ResultSet rs = cityExists.executeQuery();
        return rs.next();
    }


    public int getCoordsId(Coordinates coordinates) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM coordinates WHERE x = ? AND y = ?");
        preparedStatement.setFloat(1, coordinates.getX());
        preparedStatement.setInt(2, coordinates.getY());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO coordinates(x, y) VALUES(?, ?)");
        ps.setFloat(1, coordinates.getX());
        ps.setInt(2, coordinates.getY());
        ps.executeUpdate();
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }


    public int getGovernorId(Human governor) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM human WHERE age = ?");
        preparedStatement.setFloat(1, governor.getAge());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO human(age) VALUES(?)");
        ps.setFloat(1, governor.getAge());
        ps.executeUpdate();
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }


    public int getClimateId(Climate climate) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM climate WHERE climate_name = ?");
        preparedStatement.setString(1, climate.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }


    public int getGovernmentId(Government government)throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM government WHERE government_name = ?");
        preparedStatement.setString(1, government.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }


    public int getStandardOfLivingId(StandardOfLiving standardOfLiving) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT id FROM standard_of_living WHERE standard_of_living_name = ?");

        preparedStatement.setString(1, standardOfLiving.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    public Response loginUser(User user) throws SQLException {
        String userName = user.getName();
        byte[] hash = user.getPasswrd();
        String passwrd = new String(hash, StandardCharsets.UTF_8);
        System.out.println(passwrd);
        PreparedStatement checkLogin = this.connection.prepareStatement("SELECT * FROM users WHERE user_name = ? AND passwrd = ?");
        checkLogin.setString(1, userName);
        checkLogin.setString(2, passwrd);
        ResultSet resultSet = checkLogin.executeQuery();
        if (resultSet.next()){
            user.setId(resultSet.getInt("id"));
            return new UserResponse("Вы успешно вошли", true, user);
        }
        else {
            return new UserResponse("Такого пользователя не существует", false, user);
        }
    }


    public Response registerUser(User user) throws SQLException {
        String userName = user.getName();
        PreparedStatement checkLogin = this.connection.prepareStatement("SELECT * FROM users WHERE user_name = ?");
        checkLogin.setString(1, userName);
        ResultSet resultSet = checkLogin.executeQuery();
        byte[] hash = user.getPasswrd();
        String passwrd = new String(hash, StandardCharsets.UTF_8);
        if (resultSet.next()){
            return new UserResponse("Такой пользователь уже существует", false, user);
        }
        else {
            PreparedStatement addUser = this.connection.prepareStatement("INSERT INTO users(user_name, passwrd) VALUES (?, ?)");
            addUser.setString(1, user.getName());
            addUser.setString(2, passwrd);
            addUser.executeUpdate();
            resultSet = checkLogin.executeQuery();
            resultSet.next();
            user.setId(resultSet.getInt("id"));

            return new UserResponse("Регистрация прошла успешно", true, user);
        }
    }

    public boolean userOwner(User user, long id) throws SQLException {
        PreparedStatement userOwner = this.connection.prepareStatement("SELECT * FROM city WHERE user_id = ? AND id = ?");
        userOwner.setInt(1, user.getId());
        userOwner.setLong(2, id);
        ResultSet resultSet = userOwner.executeQuery();
        boolean isOwner = resultSet.next();
        return isOwner;
    }
}
