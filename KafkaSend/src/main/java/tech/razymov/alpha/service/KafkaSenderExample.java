package tech.razymov.alpha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tech.razymov.alpha.dto.Message;

@Component
public class KafkaSenderExample {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    KafkaSenderExample(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendMessage(Message message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }

}