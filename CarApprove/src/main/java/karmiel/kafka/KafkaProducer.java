package karmiel.kafka;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import karmiel.dto.CarAprDTO;
import karmiel.dto.CarRejDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaProducer {
    private static final String APPROVE_TOPIC = "car_approve_dto_topic";
    private static final String REJECT_TOPIC = "car_reject_dto_topic";
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendApprovalMessage(CarAprDTO carAprDTO) {
        try {
            String carAprDTOAsString = objectMapper.writeValueAsString(carAprDTO);
            logger.info("Sending approval message: {}", carAprDTOAsString);
            kafkaTemplate.send(APPROVE_TOPIC, carAprDTOAsString);
        } catch (JsonProcessingException e) {
            logger.error("Error processing CarAprDTO", e);
        }
    }

    public void sendRejectionMessage(CarRejDTO carRejDTO) {
        try {
            String carRejDTOAsString = objectMapper.writeValueAsString(carRejDTO);
            logger.info("Sending rejection message: {}", carRejDTOAsString);
            kafkaTemplate.send(REJECT_TOPIC, carRejDTOAsString);
        } catch (JsonProcessingException e) {
            logger.error("Error processing CarRejDTO", e);
        }
    }
}
