package karmiel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karmiel.dto.CarDTO;
import karmiel.entity.Auto;
import karmiel.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class CarService {

    private final CarRepository carRepository;

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
        } catch (JsonProcessingException e) {
            // Обработать ошибку преобразования JSON
            e.printStackTrace();
        }
    }

    private Auto convertToEntity(CarDTO carDto) {
        // Логика преобразования CarDto в CarEntity
        // Можно использовать библиотеку ModelMapper или выполнять преобразование вручную
        // Пример:
        Auto auto = new Auto();
        auto.setName(carDto.getBrand());
        auto.setModel(carDto.getModel());
        // Другие поля
        return auto;
    }
}


