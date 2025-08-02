package com.example.demo.messagebroker;

public interface Topic {
    void addMessage(String message);
    String getMessage();
}
