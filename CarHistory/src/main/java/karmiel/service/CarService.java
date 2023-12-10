package karmiel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karmiel.dto.CarDTO;
import karmiel.entity.Auto;
import karmiel.repo.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @KafkaListener(topics = "car_dto_topic", groupId = "car-group")
    public void consumeCarDto(String carDtoJson) {
        try {
            // Преобразовать JSON в объект CarDTO
            ObjectMapper objectMapper = new ObjectMapper();
            CarDTO carDto = objectMapper.readValue(carDtoJson, CarDTO.class);

            // Преобразовать CarDto в CarEntity и сохранить в базу данных
            Auto auto = convertToEntity(carDto);
            carRepository.save(auto);
            logger.info("Car saved successfully: {}", auto);
        } catch (JsonProcessingException e) {
            // Обработать ошибку преобразования JSON
            logger.error("Error processing CarDTO JSON", e);
            e.printStackTrace();
        }catch (Exception e) {
            // Обработать другие ошибки
            logger.error("Error processing CarDTO", e);
        }
    }

    private Auto convertToEntity(CarDTO carDto) {
        // Логика преобразования CarDto в CarEntity
        // Можно использовать библиотеку ModelMapper или выполнять преобразование вручную
        Auto auto = new Auto();
        auto.setNumber(carDto.getNumber());
        auto.setBrand(carDto.getBrand());
        auto.setModel(carDto.getModel());
        auto.setYears(carDto.getYears());
        auto.setColor(carDto.getColor());
        auto.setMileage(carDto.getMileage());
        auto.setPrice(carDto.getPrice());
        auto.setVin(carDto.getVin());
        auto.setBumper(carDto.isBumper());
        auto.setWindscreen(carDto.isWindscreen());
        auto.setClean(carDto.isClean());
        auto.setState(carDto.getState());
        return auto;
    }
}


