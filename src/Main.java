import collections.CSVDataBase;
import exceptions.FileTroubleException;
import readers.ReaderFromCSV;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // запускать программу с ключом, чтобы убрать статус сбора коллекции
        CSVDataBase dataBase = new CSVDataBase("test_this_crap.csv");
        // добавить уточнение при удалении коллекции
        // класс для чтения из файлов/бд
        // класс для получения инфы из командной строки
    }
}
