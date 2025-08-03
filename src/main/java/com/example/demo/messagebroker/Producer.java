package com.example.demo.messagebroker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final String topicName;
    private final MessageBroker broker;
    private volatile boolean running = true;
    private static final AtomicInteger producerIdCounter = new AtomicInteger(1);
    private final int producerId;

    public Producer(String topicName) {
        this.topicName = topicName;
        this.broker = MessageBroker.getInstance();
        this.producerId = producerIdCounter.getAndIncrement();
    }

    @Override
    public void run() {
        int messageCount = 0;
        while (running) {
            try {
                String message = "Message #" + (++messageCount) + " from Producer-" + producerId;
                broker.publish(topicName, message);
                System.out.println("✅ Producer-" + producerId + " published to '" + topicName + "': " + message);
                // Simulate some work/delay
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
            } catch (Exception e) {
                System.out.println(e);
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Producer-" + producerId + " shutting down.");
    }

    public void stop() {
        running = false;
    }
}
