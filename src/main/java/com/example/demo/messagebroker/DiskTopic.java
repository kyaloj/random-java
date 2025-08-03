package com.example.demo.messagebroker;

public class DiskTopic implements Topic {
    private final String name;

    public DiskTopic(String name) {
        this.name = name;
    }
    @Override
    public void addMessage(String message) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getMessage() {
        throw new RuntimeException("Not implemented");
    }
}
