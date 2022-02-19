package com.progressoft.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FileExporterImpl implements FileExporter {

    @Override
    public void exportRecord(List<String> records, Path path) {
        try {
            FileWriter fileWriter = new FileWriter(path.toFile(), true);
            for (int i = 0; i < records.size() - 1; i++) {
                fileWriter.write(records.get(i) + ",");

            }

            fileWriter.write(records.get(records.size() - 1));
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
