package com.example.demo.messagebroker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppendOnlyLog implements FileManager {
    private Path logFilePath = null;
    String resourcePath = "messagebroker/events.log";

    public AppendOnlyLog() {
        try {
            Path logDirectory = Paths.get("logs");

            // 2. Create the directory if it doesn't exist.
            if (!Files.exists(logDirectory)) {
                Files.createDirectories(logDirectory);
            }

            // 3. Define the path to your log file inside the new directory.
            this.logFilePath = logDirectory.resolve(resourcePath);
            System.out.println("Path successfully initialized: " + this.logFilePath);

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error accessing resource file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Writes a record to the end of the log file.
     * @param record The string data to write.
     * @throws IOException If a file I/O error occurs.
     */
    @Override
    public void write(String record) throws IOException {
        // StandardOpenOption.APPEND is the key to ensuring we only add to the end.
        Files.writeString(this.logFilePath, record + System.lineSeparator(),
                StandardOpenOption.APPEND);
    }

    /**
     * Reads all records from a given offset (line number).
     * @param offset The line number to start reading from (0-indexed).
     * @return A list of records from the offset onwards.
     * @throws IOException If a file I/O error occurs.
     */
    @Override
    public List<String> read(long offset) throws IOException {
        try (Stream<String> lines = Files.lines(this.logFilePath)) {
            return lines.skip(offset).collect(Collectors.toList());
        }
    }
}
