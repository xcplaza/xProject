package karmiel.producer;

import karmiel.service.DatabaseAccessor;
import karmiel.dto.CarDTO;
import karmiel.service.RandomCarGenerator;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Properties;
@Component
public class CarProducer {
    private static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC_NAME = "car_dto_topic";

    public void generateAndSendCar() {
        RandomCarGenerator carGenerator = new RandomCarGenerator();
        DatabaseAccessor databaseAccessor = new DatabaseAccessor();

        String brand = null;
        String model = null;
        try {
            brand = databaseAccessor.getBrandFromDatabase();
            model = databaseAccessor.getModelFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (brand != null && model != null) {
            CarDTO carDTO = new CarDTO(
                    carGenerator.generateRandomNumber(),
                    carGenerator.generateRandomVin(),
                    brand,
                    model,
                    Math.random() < 0.5, // Bumper
                    Math.random() < 0.5, // Windscreen
                    Math.random() < 0.5, // Clean
                    (int) (Math.random() * 61) + 40 // State (от 40 до 100)
            );
            sendToKafka(carDTO);
        }
    }

    private void sendToKafka(CarDTO carDTO) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // Используем StringSerializer

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            String carJson = convertCarDtoToJson(carDTO); // Метод для преобразования CarDTO в JSON строку
            producer.send(new ProducerRecord<>(TOPIC_NAME, carJson));
        }
    }
    private String convertCarDtoToJson(CarDTO carDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(carDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

