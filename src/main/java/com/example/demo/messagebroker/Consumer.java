package com.example.demo.messagebroker;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private final String topicName;
    private final MessageBroker broker;
    private volatile boolean running = true;
    private static final AtomicInteger consumerIdCounter = new AtomicInteger(1);
    private final int consumerId;

    public Consumer(String topicName) {
        this.topicName = topicName;
        this.broker = MessageBroker.getInstance();
        this.consumerId = consumerIdCounter.getAndIncrement();
    }

    @Override
    public void run() {
        while (running) {
            try {
                String message = broker.subscribe(topicName);
                System.out.println("🔴 Consumer-" + consumerId + " received from '" + topicName + "': " + message);
                // Simulate processing the message
                Thread.sleep(500);
            } catch (Exception e) {
                // If interrupted while waiting for a message, stop running.
                running = false;
                System.out.println(e);
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Consumer-" + consumerId + " shutting down.");
    }

    public void stop() {
        running = false;
    }
}