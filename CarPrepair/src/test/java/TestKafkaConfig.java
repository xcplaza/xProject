//import cars_project.dto.CarPrepairDto;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@TestConfiguration
//public class TestKafkaConfig {
//
//    @Bean
//    public KafkaTemplate<String, CarPrepairDto> kafkaTemplate(EmbeddedKafkaBroker embeddedKafkaBroker) {
//        // Создаем экземпляр KafkaTemplate для тестов, используя встроенный Kafka-брокер
//        return new KafkaTemplate<>(producerFactory(embeddedKafkaBroker));
//    }
//
//    private ProducerFactory<String, CarPrepairDto> producerFactory(EmbeddedKafkaBroker embeddedKafkaBroker) {
//        Map<String, Object> config = new HashMap<>();
//        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString());
//        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//}
