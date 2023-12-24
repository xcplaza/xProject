package karmiel;

import karmiel.producer.CarProducer;
import karmiel.service.DatabaseAccessor;
import karmiel.service.RandomCarGenerator;
import org.apache.kafka.clients.producer.Producer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.apache.kafka.clients.producer.ProducerRecord;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.mockito.Mockito.when;

@SpringBootTest
@SpringJUnitConfig
class CarProducerTest {

    @Mock
    private RandomCarGenerator carGenerator;

    @Mock
    private DatabaseAccessor databaseAccessor;

    @Mock
    private Producer<String, String> kafkaProducer;

    @InjectMocks
    private CarProducer carProducer;

    @Test
    void testGenerateAndSendCar() throws Exception {
        // Arrange
        when(databaseAccessor.getBrandFromDatabase()).thenReturn("Toyota");
        when(databaseAccessor.getModelFromDatabase()).thenReturn("Camry");
        when(carGenerator.generateRandomYear()).thenReturn(2020);
        when(carGenerator.generateRandomMileage()).thenReturn(50000);
        // Добавьте другие необходимые утверждения для вашего теста

        // Act
        carProducer.generateAndSendCar();

        // Assert
        verify(kafkaProducer).send(any(ProducerRecord.class));
    }
}
