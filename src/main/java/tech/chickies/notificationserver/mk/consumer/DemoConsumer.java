package tech.chickies.notificationserver.mk.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemoConsumer {
    @KafkaListener(topics = "test")
    public void consume(String message) {
        // Process the Kafka message here
        System.out.println("Received message: " + message);
    }
}
