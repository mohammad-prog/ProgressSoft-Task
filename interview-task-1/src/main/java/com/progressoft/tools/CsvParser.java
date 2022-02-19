package com.progressoft.tools;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CsvParser implements FileParser {

    @Override
    public List<String[]> parse(Path path) {

        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(path.toFile()))) {
            r = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }


        return r;
    }
}
