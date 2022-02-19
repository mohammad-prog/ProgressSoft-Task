package com.progressoft.tools;

import java.nio.file.Path;
import java.util.List;

public interface FileParser {
    List<String[]> parse(Path path);
}
