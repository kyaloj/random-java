package com.example.demo.messagebroker;

import java.io.IOException;
import java.util.List;

public interface FileManager {
    void write(String record) throws IOException;
    List<String> read(long offset) throws IOException;
}
