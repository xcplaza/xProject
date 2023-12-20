//import cars_project.Main;
//import cars_project.dto.CarAprDTO;
//import cars_project.kafkaconfig.KafkaProdConfig;
//import cars_project.service.CarPrepairService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//
//import static org.mockito.ArgumentMatchers.any;
//
//@SpringBootTest(classes = TestKafkaConfig.class)
//@EmbeddedKafka(partitions = 1, topics = {"car-apr-topic"})
//class CarServiceTest {
//
//    @Autowired
//    private KafkaTemplate<String, CarAprDTO> kafkaTemplate;
//
//    @Autowired
//    @InjectMocks
//    private CarPrepairService carService;
//
//    @Test
//    void testConsumeCarAprDTO() throws InterruptedException {
//        // Создаем тестовый объект CarAprDTO
//        CarAprDTO carAprDTO = createSampleCarAprDTO();
//
//        Mockito.when(kafkaTemplate.send(any(), any())).thenReturn(null);
//
//        carService.processCarAprDTO(carAprDTO);
//
//        Thread.sleep(1000);
//
//        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(any(), any());
//    }
//
//    private CarAprDTO createSampleCarAprDTO() {
//        CarAprDTO carAprDTO = new CarAprDTO();
//        carAprDTO.setNumber(1);
//        carAprDTO.setVin("123456");
//        carAprDTO.setBrand("Toyota");
//        carAprDTO.setModel("Camry");
//        carAprDTO.setBumper(false);
//        carAprDTO.setWindscreen(true);
//        carAprDTO.setClean(false);
//        carAprDTO.setState(1);
//        carAprDTO.setColor("Red");
//        carAprDTO.setYears(2020);
//        carAprDTO.setMileage(50000);
//        carAprDTO.setPrice(25000);
//        return carAprDTO;
//    }
//}