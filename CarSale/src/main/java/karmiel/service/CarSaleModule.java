package karmiel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import karmiel.entity.Car;
import karmiel.entity.Sale;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CarSaleModule {

    private static final String TOPIC = "car_ready";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "car_sale_group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(TOPIC));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Car car = objectMapper.readValue(record.value(), Car.class);

                    // Добавление объекта в БД sale
                    persistCarToDatabase(car);

                    // Вычисление окончательной стоимости авто и добавление в БД sale
                    calculateAndPersistFinalPrice(car);

                    // Добавление скидки в БД sale
                    addDiscountIfNotSold(car);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void persistCarToDatabase(Car car) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Sale sale = new Sale();
        sale.setNumber(car.getNumber());
        sale.setVin(car.getVin());
        sale.setBrand(car.getBrand());
        sale.setModel(car.getModel());
        sale.setBumper(car.isBumper());
        sale.setWindscreen(car.isWindscreen());
        sale.setClean(car.isClean());
        sale.setState(car.getState());
        sale.setColor(car.getColor());
        sale.setYears(car.getYears());
        sale.setMileage(car.getMileage());
        sale.setPrice(car.getPrice());

        entityManager.persist(sale);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }


    private static void calculateAndPersistFinalPrice(Car car) {
        int state = car.getState();
        int numReplacedParts = car.getCarPartsList().size();

        int maxFinalPrice = car.getPrice() * 2;
        int finalPrice = Math.min(maxFinalPrice, state * 1000 - numReplacedParts * 500);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Sale sale = entityManager.find(Sale.class, car.getNumber());
        if (sale != null) {
            sale.setFinalPrice(finalPrice);
            entityManager.merge(sale);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    private static void addDiscountIfNotSold(Car car) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Sale sale = entityManager.find(Sale.class, car.getNumber());
        if (sale != null && sale.getFinalPrice() > 0 && sale.getDiscount() == 0) {
            long hoursSinceSale = TimeUnit.MILLISECONDS.toHours(
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                            sale.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            );

            if (hoursSinceSale >= 1 && hoursSinceSale <= 24) {
                int discount = (int) (0.3 * sale.getFinalPrice());
                sale.setDiscount(discount);
                entityManager.merge(sale);
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
