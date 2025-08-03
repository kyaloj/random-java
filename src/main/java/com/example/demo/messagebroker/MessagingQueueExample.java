package com.example.demo.messagebroker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MessagingQueueExample {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Messaging Queue Simulation...");

        // Get the singleton broker instance
        MessageBroker broker = MessageBroker.getInstance();

        // Define a topic
        final String topicName = "user-events";
        broker.createTopic(topicName, TopicStoreType.IN_MEMORY);

        // Use an ExecutorService for better thread management
        ExecutorService executor = Executors.newCachedThreadPool();

        // Create and start producers
        List<Producer> producers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Producer p = new Producer(topicName);
            producers.add(p);
            executor.submit(p);
        }

        // Create and start consumers
        List<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Consumer c = new Consumer(topicName);
            consumers.add(c);
            executor.submit(c);
        }

        // Let the simulation run for 10 seconds
        TimeUnit.SECONDS.sleep(10);

        // Stop producers and consumers
        System.out.println("\n--- Shutting down ---");
        producers.forEach(Producer::stop);
        consumers.forEach(Consumer::stop);

        // Shutdown the executor
        // shutdown() waits for tasks to finish, shutdownNow() interrupts them.
        executor.shutdownNow();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.err.println("Executor did not terminate in the specified time.");
        }

        System.out.println("Simulation finished.");
    }
}
