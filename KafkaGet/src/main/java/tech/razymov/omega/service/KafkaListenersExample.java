package tech.razymov.omega.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tech.razymov.omega.dto.Message;

@Component
class KafkaListenersExample {


    @KafkaListener(topics = "demo_topic", groupId = "myGroup")
    void listener(Message data) {

        System.out.println(data);

    }

}