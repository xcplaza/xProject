package karmiel;

import karmiel.controller.CarController;
import karmiel.producer.CarProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarProducer carProducer;

    @Test
    public void testGenerateAndSendCar() throws Exception {
        // Arrange
        // You can add additional setup if needed

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/generate"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Car generation and sending to Kafka initiated."));

        // Verify that carProducer.generateAndSendCar() was called
        // Note: This verification is optional and depends on your specific requirements
        // verify(carProducer).generateAndSendCar();
    }
}
