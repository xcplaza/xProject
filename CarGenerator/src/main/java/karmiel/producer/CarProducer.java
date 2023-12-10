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
            int year = generateRandomYear();
            int mileage = generateRandomMileage();
            int state = calculateState(year, mileage);
            int price = generateRandomPrice(year, mileage, state);

            CarDTO carDTO = new CarDTO(
                    carGenerator.generateRandomNumber(),
                    carGenerator.generateRandomVin(),
                    brand,
                    model,
                    Math.random() < 0.5, // Bumper
                    Math.random() < 0.5, // Windscreen
                    Math.random() < 0.5, // Clean
                    state,
                    generateRandomColor(), // Color
                    year,
                    mileage,
                    price
            );
            sendToKafka(carDTO);
        }
    }

    private int generateRandomYear() {
        int currentYear = LocalDate.now().getYear();
        int startYear = 1950;
        return (int) (Math.random() * (currentYear - startYear + 1)) + startYear;
    }

    private int generateRandomMileage() {
        return (int) (Math.random() * 300001); // Генерация пробега от 0 до 300,000 км
    }

    private int calculateState(int year, int mileage) {
        // Логика расчета состояния в зависимости от года выпуска и пробега
        int baseState = 100; // Максимальное состояние

        // Чем старше год выпуска и больше пробег - тем ниже состояние
        int agePenalty = Math.max(0, LocalDate.now().getYear() - year); // баллы за возраст
        int mileagePenalty = Math.min(mileage / 10000, 50); // баллы за пробег (максимум 50)

        // Уменьшаем баллы, если он приводит к отрицательному результату
        agePenalty = Math.min(agePenalty, baseState);
        mileagePenalty = Math.min(mileagePenalty, baseState);

        return baseState - agePenalty - mileagePenalty;
    }

    private String generateRandomColor() {
        String[] colors = {"Red", "Blue", "Green", "White", "Black", "Silver", "Yellow", "Orange", "Purple"};
        int randomIndex = (int) (Math.random() * colors.length);
        return colors[randomIndex];
    }

    private int generateRandomPrice(int year, int mileage, int state) {
        // Логика генерации цены в зависимости от года, пробега и состояния авто
        // Пример простой логики: чем старше год, больше пробег и ниже состояние - тем дешевле
        int basePrice = 10000; // Минимальная цена

        int ageBonus = Math.max(0, year - 2000); // бонус за возраст (год выпуска после 2000)
        int mileageBonus = Math.max(0, 300000 - mileage) / 10000; // бонус за пробег (максимум 30,000 км)
        int statePenalty = Math.max(0, 100 - state); // баллы за состояние

        return basePrice + ageBonus + mileageBonus - statePenalty;
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
