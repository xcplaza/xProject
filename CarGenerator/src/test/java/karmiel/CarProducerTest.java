package karmiel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karmiel.dto.CarDTO;
import karmiel.producer.CarProducer;
import karmiel.service.DatabaseAccessor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "car_dto_topic")
class CarProducerTest {

    @Autowired
    private CarProducer carProducer;

    @MockBean
    private DatabaseAccessor databaseAccessor;

    private Consumer<String, String> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group-id", "true", "localhost:9092"); // Замените на ваш актуальный порт
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("car_dto_topic"));
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void testGenerateAndSendCar() throws Exception {
        // Mock databaseAccessor to return fixed values
         when(databaseAccessor.getBrandFromDatabase()).thenReturn("Toyota");
         when(databaseAccessor.getModelFromDatabase()).thenReturn("Camry");

        // Generate and send car
        carProducer.generateAndSendCar();

        // Wait for the Kafka record to be consumed
        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer);

        // Assert that one record is received
        assertEquals(1, records.count());

        // Process the received record
        for (ConsumerRecord<String, String> record : records) {
            String receivedCarJson = record.value();
            // Deserialize JSON to CarDTO or perform other assertions as needed
            // For simplicity, assuming you have a method to deserialize JSON to CarDTO
            CarDTO receivedCarDTO = deserializeCarJson(receivedCarJson);

            // Perform assertions on receivedCarDTO
            assertEquals("ExpectedBrand", receivedCarDTO.getBrand());
            assertEquals("ExpectedModel", receivedCarDTO.getModel());
            // Add more assertions based on your requirements
        }
    }

    private CarDTO deserializeCarJson(String carJson) throws JsonProcessingException {
        // Implement JSON deserialization logic using your ObjectMapper
        // For simplicity, assuming you have a method to deserialize JSON to CarDTO
         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.readValue(carJson, CarDTO.class);
    }
}
