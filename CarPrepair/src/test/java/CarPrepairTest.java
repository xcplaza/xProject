import cars_project.dto.CarAprDTO;
import cars_project.dto.CarNotReadyDto;
import cars_project.dto.CarReadyDto;
import cars_project.service.CarPrepairService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarPrepairServiceTest {

    @InjectMocks
    private CarPrepairService carPrepairService;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessCarAprDTO_NotReady() {

        CarAprDTO carAprDTO = createSampleCarAprDTO(false, false, false);
        carPrepairService.processCarAprDTO(carAprDTO);
        verify(kafkaTemplate).send(eq("car_dto_not_ready"), any(CarNotReadyDto.class));
        verify(kafkaTemplate, never()).send(eq("car_dto_ready"), any(CarReadyDto.class));
        CarNotReadyDto carNotReadyDto= carPrepairService.createCarNotReadyDto(carAprDTO);

        assertTrue(carNotReadyDto.getColor().equals("Purple"));
        assertTrue(carNotReadyDto.getNumber()==844337);
        assertTrue(carNotReadyDto.getVin().equals("95WTEPB856DJ771AO"));
        assertTrue(carNotReadyDto.getBrand().equals("Isuzu"));
        assertTrue(carNotReadyDto.getState()==68);
        assertTrue(carNotReadyDto.getYears()==2012);
        assertTrue(carNotReadyDto.getMileage()==211940);
        assertTrue(carNotReadyDto.getPrice()==9988);
    }

    @Test
    void testProcessCarAprDTO_Ready() {
        CarAprDTO carAprDTO = createSampleCarAprDTO(true, true, true);
        carPrepairService.processCarAprDTO(carAprDTO);
        verify(kafkaTemplate).send(eq("car_dto_ready"), any(CarReadyDto.class));
        verify(kafkaTemplate, never()).send(eq("car_dto_not_ready"), any(CarNotReadyDto.class));
        CarReadyDto carReadyDto = carPrepairService.createCarReadyDto(carAprDTO);
        assertTrue(carReadyDto.getColor().equals("Purple"));
        assertTrue(carReadyDto.getNumber()==844337);
        assertTrue(carReadyDto.getVin().equals("95WTEPB856DJ771AO"));
        assertTrue(carReadyDto.getBrand().equals("Isuzu"));
        assertTrue(carReadyDto.getState()==68);
        assertTrue(carReadyDto.getYears()==2012);
        assertTrue(carReadyDto.getMileage()==211940);
        assertTrue(carReadyDto.getPrice()==9988);


    }

    @Test
    void testCreateCarPartsList() {
        CarAprDTO carAprDTO = createSampleCarAprDTO(false, true, false);

        List<String> carPartsList = carPrepairService.createCarPartsList(carAprDTO);

        assertEquals(2, carPartsList.size());
        assertTrue(carPartsList.contains("bumper"));
        assertTrue(carPartsList.contains("clean"));
    }





    private CarAprDTO createSampleCarAprDTO(boolean isBumper, boolean isWindscreen, boolean isClean) {
        CarAprDTO carAprDTO = new CarAprDTO();
        carAprDTO.setNumber(844337);
        carAprDTO.setVin("95WTEPB856DJ771AO");
        carAprDTO.setBrand("Isuzu");
        carAprDTO.setModel("2717");
        carAprDTO.setBumper(isBumper);
        carAprDTO.setWindscreen(isWindscreen);
        carAprDTO.setClean(isClean);
        carAprDTO.setState(68);
        carAprDTO.setColor("Purple");
        carAprDTO.setYears(2012);
        carAprDTO.setMileage(211940);
        carAprDTO.setPrice(9988);

        return carAprDTO;
    }
}