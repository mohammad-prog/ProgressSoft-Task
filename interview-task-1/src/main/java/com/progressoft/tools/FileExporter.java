package com.progressoft.tools;

import java.nio.file.Path;
import java.util.List;

public interface FileExporter {
    void exportRecord(List<String> records, Path path);
}
