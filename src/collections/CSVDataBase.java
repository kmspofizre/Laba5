package collections;

import components.City;
import exceptions.FileTroubleException;
import readers.ReaderFromCSV;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public class CSVDataBase extends DataBase{
    public CSVDataBase(String file_name){};
    // разбор параметров из csv
    @Override
    public TreeMap<Integer, City> getData(){
        try {
            List<String []> parsed = ReaderFromCSV.readFromCSV("test_this_crap.csv");
            for (String[] elem : parsed){
                for (String elem1 : elem){
                    System.out.println(elem1);
                }
            }
        }
        catch (IOException e){
            System.out.println("Не удалось найти файл");
        }
        catch (FileTroubleException exc){
            System.out.println(exc.getMessage());
        }
        return null;
    };
}
