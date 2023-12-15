package karmiel;

import karmiel.dto.CarDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarDTOTest {

    @Test
    public void testCarDTO() {
        // Arrange
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

        // Act
        CarDTO carDTO = new CarDTO(number, vin, brand, model, bumper, windscreen, clean, state, color, years, mileage, price);

        // Assert
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
        // Arrange
        CarDTO carDTO = new CarDTO();

        // Act
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

        // Assert
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
    }}

