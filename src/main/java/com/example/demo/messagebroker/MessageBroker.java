package com.example.demo.messagebroker;

import java.util.Map;
import java.util.concurrent.*;

/**
 * The central message broker. It manages topics and the message flow.
 * Implemented as a thread-safe singleton.
 */
public class MessageBroker {

    private static final MessageBroker INSTANCE = new MessageBroker();
    // A map where the key is the topic name and the value is the Topic object.
    private final Map<String, Topic> topics = new ConcurrentHashMap<>();
    private final FileManager log = new AppendOnlyLog();

    private MessageBroker() {}

    public static MessageBroker getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new topic if it doesn't already exist.
     * @param name The name of the topic.
     */
    public void createTopic(String name, TopicStoreType storeType) {
        Topic topic = switch (storeType) {
            case DISK -> new DiskTopic(name, log);
            case IN_MEMORY -> new InMemoryTopic(name);
        };

        topics.putIfAbsent(name, topic);
    }

    /**
     * Publishes a message to a specific topic.
     * Called by Producers.
     * @param topicName The topic to publish to.
     * @param message The message content.
     */
    public void publish(String topicName, String message) {
        Topic topic = topics.get(topicName);
        if (topic == null) {
            System.out.println("WARN: Producer tried to publish to non-existent topic: " + topicName);
            return;
        }
        topic.addMessage(message);
    }

    /**
     * Subscribes to a topic to receive one message.
     * This is a "pull" model where the consumer asks for a message.
     * This call will block until a message is available.
     * Called by Consumers.
     * @param topicName The topic to subscribe to.
     * @return The message content.
     */
    public String subscribe(String topicName) throws InterruptedException {
        Topic topic = topics.get(topicName);
        if (topic == null) {
            throw new IllegalArgumentException("Topic does not exist: " + topicName);
        }
        return topic.getMessage();
    }
}
