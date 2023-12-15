//package karmiel;
//
//import karmiel.producer.CarProducer;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.kafka.test.utils.ContainerTestUtils;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//
//import java.util.Collections;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
//@EnableKafka
//public class CarProducerTest {
//
//    @Autowired
//    private CarProducer carProducer;
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Test
//    public void testGenerateAndSendCar() throws Exception {
//        // Arrange
//        ContainerTestUtils.waitForAssignment(new MyConsumerRebalanceListener(), embeddedKafkaBroker.getPartitionsPerTopic());
//
//        // Act
//        carProducer.generateAndSendCar();
//
//        // Assert
//        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
//        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//
//        Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<>(consumerProps).createConsumer();
//        consumer.subscribe(Collections.singleton("car_dto_topic"));
//
//        ConsumerRecords<String, String> records = KafkaTestUtils.pollRecords(consumer, 5000);
//
//        // Ваши дополнительные проверки, например, сравнение с ожидаемыми значениями
//        // ...
//
//        assertEquals(1, records.count());
//
//        // Verify that databaseAccessor.getBrandFromDatabase() and databaseAccessor.getModelFromDatabase() were called
//        // Note: This verification is optional and depends on your specific requirements
//        // verify(databaseAccessor).getBrandFromDatabase();
//        // verify(databaseAccessor).getModelFromDatabase();
//    }
//
//    private static class MyConsumerRebalanceListener implements ConsumerRebalanceListener {
//        @Override
//        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {}
//
//        @Override
//        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {}
//    }
//}
//
//
