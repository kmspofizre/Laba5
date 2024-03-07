import readers.ReaderFromCSV;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        // класс для чтения из файлов/бд
        // класс для получения инфы из командной строки
        try {
            ReaderFromCSV.readFromCSV("test_this_crap.csv");
        }
        catch (IOException e){
            System.out.println("дурной?");
            System.out.println(e);
        }
    }
}
