package readers;


import java.io.*;
import java.nio.charset.StandardCharsets;
import exceptions.FileTroubleException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.Arrays;
import java.util.List;

public class ReaderFromCSV extends DataReader {

    public static void readFromCSV(String path) throws IOException, FileTroubleException {
        Reader in = new FileReader(path, StandardCharsets.UTF_8);
        CSVParser parser = CSVParser.parse(in, CSVFormat.RFC4180);
        List<CSVRecord> records = parser.getRecords();
        int massiveLength = records.get(0).values().length;
        for (CSVRecord elem : records) {
            System.out.println(Arrays.toString(elem.values()));
            if (elem.values().length != massiveLength){
                throw new FileTroubleException("Неверные данные. Количество столбцов несопоставимо");
            }
        }
        // unchecked exception

    }
}
