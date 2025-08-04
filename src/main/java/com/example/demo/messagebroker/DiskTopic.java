package com.example.demo.messagebroker;

import java.io.IOException;
import java.util.List;

public class DiskTopic implements Topic {
    private final String name;
    private final FileManager fileManager;
    private long offset = 0l;

    public DiskTopic(String name, FileManager fileManager) {
        this.name = name;
        this.fileManager = fileManager;
    }
    @Override
    public void addMessage(String message) {
        try {
            System.out.println("Writing message");
            fileManager.write(message);
            System.out.println("Message written: " + message);
        } catch (IOException e) {
            System.out.println("addMessage Errror");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMessage() {
        List<String> results;
        try {
            results = fileManager.read(offset);
            System.out.println(results);
        } catch (IOException e) {
            System.out.println("getMessage Errror");
            throw new RuntimeException(e);
        }

        return results.get(results.size() - 1);
    }
}
