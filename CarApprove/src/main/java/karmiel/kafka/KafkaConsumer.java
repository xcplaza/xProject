package karmiel.kafka;

import karmiel.service.CarProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaConsumer {
    private final CarProcessingService carProcessingService;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    public KafkaConsumer(CarProcessingService carProcessingService) {
        this.carProcessingService = carProcessingService;
    }

    @KafkaListener(topics = "car_dto_topic")
    public void consume(@Payload String carDTOAsString) {
        try {
            logger.info("Received carDTO: {}", carDTOAsString);
            carProcessingService.processCar(carDTOAsString);
        } catch (Exception e) {
            logger.error("Error processing carDTO", e);
        }
    }
}
