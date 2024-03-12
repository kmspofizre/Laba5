package writers;

import components.City;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.TreeMap;

public class CSVWriter implements DataWriter{
    public static boolean writeCityCollectionToCSV(String fileName, TreeMap<Long, City> dataToWrite) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
        CSVPrinter printer = new CSVPrinter(bw, CSVFormat.RFC4180);
        for (City city : dataToWrite.values()){
            printer.printRecord(city.getId(), city.getName(), city.getCoordinates().getX().toString(),
                    city.getCoordinates().getY().toString(), city.getArea().toString(), city.getPopulation().toString(),
                    city.getMetersAboveSeaLevel().toString(), city.getClimate().toString(),
                    city.getGovernment().toString(), city.getStandardOfLiving().toString(),
                    city.getGovernor().toString());
        }
        return true;
    }
}
