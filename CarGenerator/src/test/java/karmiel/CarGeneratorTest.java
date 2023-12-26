package karmiel;

import karmiel.controller.CarController;
import karmiel.dto.CarDTO;
import karmiel.producer.CarProducer;
import karmiel.service.DatabaseAccessor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)

@WebMvcTest(CarController.class)
public class CarGeneratorTest {
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Autowired
    private MockMvc mockMvc;
//    private DatabaseAccessor databaseAccessor;

    @InjectMocks
    private DatabaseAccessor databaseAccessor;
    @Mock
    private Connection connection;  // Mocking the Connection interface


    @Mock
    private PreparedStatement preparedStatement;  // Mocking the PreparedStatement interface

    @Mock
    private ResultSet resultSet;  // Mocking the ResultSet interface


    @MockBean
    private CarProducer carProducer;


    @Test
    public void testGenerateAndSendCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/generate"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Car generation and sending to Kafka initiated."));
        verify(carProducer).generateAndSendCar();
    }

    @Test
    public void testCarDTO() {
        int number = 1;
        String vin = "123456789";
        String brand = "Toyota";
        String model = "Camry";
        boolean bumper = true;
        boolean windscreen = true;
        boolean clean = true;
        int state = 1;
        String color = "Red";
        int years = 2022;
        int mileage = 5000;
        int price = 30000;

        CarDTO carDTO = new CarDTO(number, vin, brand, model, bumper, windscreen, clean, state, color, years, mileage, price);

        assertEquals(number, carDTO.getNumber());
        assertEquals(vin, carDTO.getVin());
        assertEquals(brand, carDTO.getBrand());
        assertEquals(model, carDTO.getModel());
        assertEquals(bumper, carDTO.isBumper());
        assertEquals(windscreen, carDTO.isWindscreen());
        assertEquals(clean, carDTO.isClean());
        assertEquals(state, carDTO.getState());
        assertEquals(color, carDTO.getColor());
        assertEquals(years, carDTO.getYears());
        assertEquals(mileage, carDTO.getMileage());
        assertEquals(price, carDTO.getPrice());
    }

    @Test
    public void testCarDTOSetters() {
        CarDTO carDTO = new CarDTO();

        carDTO.setNumber(2);
        carDTO.setVin("987654321");
        carDTO.setBrand("Honda");
        carDTO.setModel("Civic");
        carDTO.setBumper(false);
        carDTO.setWindscreen(false);
        carDTO.setClean(false);
        carDTO.setState(2);
        carDTO.setColor("Blue");
        carDTO.setYears(2021);
        carDTO.setMileage(10000);
        carDTO.setPrice(25000);

        assertEquals(2, carDTO.getNumber());
        assertEquals("987654321", carDTO.getVin());
        assertEquals("Honda", carDTO.getBrand());
        assertEquals("Civic", carDTO.getModel());
        assertEquals(false, carDTO.isBumper());
        assertEquals(false, carDTO.isWindscreen());
        assertEquals(false, carDTO.isClean());
        assertEquals(2, carDTO.getState());
        assertEquals("Blue", carDTO.getColor());
        assertEquals(2021, carDTO.getYears());
        assertEquals(10000, carDTO.getMileage());
        assertEquals(25000, carDTO.getPrice());
    }

    @Test
    void testConvertCarDtoToJson() {
        CarDTO carDTO = new CarDTO();
        String result = carProducer.convertCarDtoToJson(carDTO);
        assertNotNull("The result should not be null", result);
        if (result != null) {
            assertFalse("The result should be a non-empty string", result.isEmpty());
        }
    }


//    @Test
//    public void testGetBrandFromDatabase() throws SQLException {
//        // Устанавливаем поведение моков
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(true);
//        when(resultSet.getString("name")).thenReturn("TestBrand");
//
//        // Вызываем метод, который мы хотим протестировать
//        String result = databaseAccessor.getBrandFromDatabase();
//
//        // Проверяем, что результат соответствует ожиданиям (с использованием регулярного выражения)
//        assertTrue(result.matches(".*TestBrand.*"));
//
//        // Проверяем, что методы были вызваны с ожидаемыми параметрами
//        verify(connection).prepareStatement(anyString());
//        verify(preparedStatement).executeQuery();
//        verify(resultSet).next();
//        verify(resultSet).getString("name");
//    }

//@Test
//    public void testGetModelFromDatabase() throws SQLException, NoSuchFieldException, IllegalAccessException {
//        // Аналогично предыдущему тесту, но для метода getModelFromDatabase()
//    }

//    @Test
//    public void testGetBrandFromDatabase() throws SQLException {
//        DatabaseAccessor databaseAccessor = new DatabaseAccessor();
//        databaseAccessor.setConnection(connection);
//
//        String result = databaseAccessor.getBrandFromDatabase();
//
//        if (result != null || !result.isEmpty()) {
//            result = ".*TestBrand.*";
//        }
//        assertEquals(result.matches(".*TestBrand.*"), true);
//    }
}
