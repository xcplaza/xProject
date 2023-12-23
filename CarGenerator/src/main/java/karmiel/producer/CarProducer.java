package karmiel.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import karmiel.dto.CarDTO;
import karmiel.service.DatabaseAccessor;
import karmiel.service.RandomCarGenerator;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

@Component
public class CarProducer {
    private static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC_NAME = "car_dto_topic";

    public void generateAndSendCar() throws SQLException {
        RandomCarGenerator carGenerator = new RandomCarGenerator();
        DatabaseAccessor databaseAccessor = new DatabaseAccessor();

        String brand = null;
        String model = null;
        try {
            brand = databaseAccessor.getBrandFromDatabase();
            model = databaseAccessor.getModelFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Прокидываем исключение выше
        }

        if (brand != null && model != null) {
            int year = carGenerator.generateRandomYear();
            int mileage = carGenerator.generateRandomMileage();
            int state = carGenerator.calculateState(year, mileage);
            int price = carGenerator.generateRandomPrice(year, mileage, state);

            CarDTO carDTO = new CarDTO(
                    carGenerator.generateRandomNumber(),
                    carGenerator.generateRandomVin(),
                    brand,
                    model,
                    Math.random() < 0.5, // Bumper
                    Math.random() < 0.5, // Windscreen
                    Math.random() < 0.5, // Clean
                    state,
                    carGenerator.generateRandomColor(),
                    year,
                    mileage,
                    price
            );
            sendToKafka(carDTO);
        }
    }

    public void sendToKafka(CarDTO carDTO) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // Используем StringSerializer

        try (Producer<String, String> producer = new KafkaProducer<>(properties)) {
            String carJson = convertCarDtoToJson(carDTO); // Метод для преобразования CarDTO в JSON строку
            producer.send(new ProducerRecord<>(TOPIC_NAME, carJson));
        }
    }

    public String convertCarDtoToJson(CarDTO carDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(carDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
