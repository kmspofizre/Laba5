package utils;

import components.*;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class CityCollectionMaker extends CollectionMaker{
    public static TreeMap<Long, City> makeCityCollection(List<String []> collectionData){
         TreeMap<Long, City> collection = new TreeMap<>();
         long id;
         for (String [] item : collectionData){
             id = Long.parseLong(item[0]);
             City city = makeCityInstance(item);
             collection.put(id, city);
         }
         return collection;
    }
    public static Climate getClimate(String climate){
        Climate outputClimate = switch (climate) {
            case ("MONSOON") -> Climate.MONSOON;
            case ("MEDITERRANIAN") -> Climate.MEDITERRANIAN;
            case ("SUBARCTIC") -> Climate.SUBARCTIC;
            case ("DESERT") -> Climate.DESERT;
            default -> null;
        };
        return outputClimate;
    }
    public static Government getGovernment(String government){
        Government outputGovernment = switch (government) {
            case ("DESPOTISM") -> Government.DESPOTISM;
            case ("DICTATORSHIP") -> Government.DICTATORSHIP;
            case ("STRATOCRACY") -> Government.STRATOCRACY;
            default -> null;
        };
        return outputGovernment;
    }
    public static StandardOfLiving getStandardOfLiving(String standardOfLiving){
        StandardOfLiving outputStandardOfLiving = switch (standardOfLiving) {
            case ("HIGH") -> StandardOfLiving.HIGH;
            case ("LOW") -> StandardOfLiving.LOW;
            case ("NIGHTMARE") -> StandardOfLiving.NIGHTMARE;
            default -> null;
        };
        return outputStandardOfLiving;
    }
    public static City makeCityInstance(String[] item){
        Human governor = new Human(Integer.parseInt(item[10]));
        Climate climate = getClimate(item[7]);
        Government government = getGovernment(item[8]);
        StandardOfLiving standardOfLiving = getStandardOfLiving(item[9]);
        Coordinates coords = new Coordinates(Float.parseFloat(item[2]), Integer.parseInt(item[3]));
        Date date = new Date();
        long id = Long.parseLong(item[0]);
        int area = Integer.parseInt(item[4]);
        int population = Integer.parseInt(item[5]);
        String name = item[1];
        Double metersAboveSeaLevel = Double.parseDouble(item[6]);
        City city = new City(id, name, coords, date, area,
                population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
        return city;
    }
}
