package com.example.demo.messagebroker;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a single topic queue. It uses a BlockingQueue for thread-safe message handling.
 */
public class InMemoryTopic implements Topic{
    private final String name;
    // Using BlockingQueue handles all the synchronization and waiting.
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public InMemoryTopic(String name) {
        this.name = name;
    }

    public void addMessage(String message) {
        // offer() is non-blocking and returns false if it can't add.
        // For an unbounded queue like LinkedBlockingQueue, it always succeeds.
        queue.offer(message);
    }

    public String getMessage() {
        // take() is a blocking call. It will wait until a message is available in the queue.
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
