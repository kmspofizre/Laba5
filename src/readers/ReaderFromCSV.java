package readers;


import java.io.*;
import java.nio.charset.StandardCharsets;
import exceptions.FileTroubleException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class ReaderFromCSV implements DataReader {
    public static List<String[]> readFromCSV(String path) throws IOException, FileTroubleException {
        Reader in = new FileReader(path, StandardCharsets.UTF_8);
        CSVParser parser = CSVParser.parse(in, CSVFormat.RFC4180);
        List<CSVRecord> records = parser.getRecords();
        List<String []> parsed = new ArrayList<>();
        int massiveLength = records.get(0).values().length;
        for (CSVRecord elem : records) {
            if (!(elem.values().length == massiveLength)){
                throw new FileTroubleException("Неверные данные. Столбцы несопоставимы друг с другом");
            }
            parsed.add(elem.values());
        }
        return parsed;
    }
}
