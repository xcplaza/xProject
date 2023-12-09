import cars_project.dto.CarAprDTO;
import cars_project.dto.CarPrepairDto;
import cars_project.service.CarPrepairService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class CarPrepairTest {
    @MockBean
    private KafkaTemplate<String, CarPrepairDto> kafkaTemplate;
    @Autowired
    private CarPrepairService carPrepairService;


    @Test
    void CarPrepairServiceTest(){
        CarAprDTO carAprDTO = new CarAprDTO();
        carAprDTO.setVin("B25F6VD8AD81Z9WY2");
        carAprDTO.setBrand("Lada");
        carAprDTO.setModel("Niva");
        carAprDTO.setBumper(false);
        carAprDTO.setWindscreen(true);
        carAprDTO.setClean(true);
        carAprDTO.setState(48);
        carAprDTO.setArpooved(true);


    }

}
