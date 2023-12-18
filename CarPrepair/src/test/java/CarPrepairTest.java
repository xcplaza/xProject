import cars_project.dto.CarAprDTO;
import cars_project.dto.CarParts;
import cars_project.dto.CarPrepairDto;
import cars_project.service.CarPrepairService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;

import static org.mockito.Mockito.*;

class CarPrepairServiceTest {
@Mock
private KafkaTemplate<String, CarPrepairDto> kafkaTemplate;

@InjectMocks
private CarPrepairService carPrepairService;

@BeforeEach
    void setUp() {
            MockitoAnnotations.initMocks(this);
            }

@Test
    void processCarAprDTO_ShouldSendCarPrepairDTOToKafka() {
            // Arrange
            CarAprDTO carAprDTO = new CarAprDTO();
            carAprDTO.setNumber(1);
            carAprDTO.setVin("ABC123");
            carAprDTO.setBrand("Toyota");
            carAprDTO.setModel("Camry");
            carAprDTO.setBumper(false);
            carAprDTO.setWindscreen(true);
            carAprDTO.setClean(true);
            carAprDTO.setState(70);
            carAprDTO.setColor("Blue");
            carAprDTO.setYears(2022);
            carAprDTO.setMileage(5000);
            carAprDTO.setPrice(25000);

            ConsumerRecord<String, CarAprDTO> consumerRecord = new ConsumerRecord<>(
        "car_approve_dto_topic",
        0,
        0L,
        "key",
        carAprDTO
        );

        // Act
    // carPrepairService.processCarAprDTO(consumerRecord);

        // Assert
        CarPrepairDto expectedCarPrepairDTO = new CarPrepairDto();
        expectedCarPrepairDTO.setNumber(1);
        expectedCarPrepairDTO.setVin("ABC123");
        expectedCarPrepairDTO.setBrand("Toyota");
        expectedCarPrepairDTO.setModel("Camry");
        expectedCarPrepairDTO.setBumper(false);
        expectedCarPrepairDTO.setWindscreen(true);
        expectedCarPrepairDTO.setClean(true);
        expectedCarPrepairDTO.setState(70);
        expectedCarPrepairDTO.setColor("Blue");
        expectedCarPrepairDTO.setYears(2022);
        expectedCarPrepairDTO.setMileage(5000);
        expectedCarPrepairDTO.setPrice(25000);
        expectedCarPrepairDTO.setCarPartsList(Collections.singletonList(new CarParts("Bumper")));

        verify(kafkaTemplate, times(1)).send(eq("car_dto_ready"), eq(expectedCarPrepairDTO));
        }
        }