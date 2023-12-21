package cars_project.service;

import cars_project.dto.CarAprDTO;
import cars_project.dto.CarNotReadyDto;
import cars_project.dto.CarReadyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

@Service
public class CarPrepairService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "car_approve_dto_topic", groupId = "car-prepair")
    public void consumeCarAprDTO(String carAprDTOJson) {
        try {
            CarAprDTO carAprDTO = objectMapper.readValue(carAprDTOJson, CarAprDTO.class);
            processCarAprDTO(carAprDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processCarAprDTO(CarAprDTO carAprDTO) {
        if (!carAprDTO.isBumper() || !carAprDTO.isClean() || !carAprDTO.isWindscreen()) {
            CarNotReadyDto carNotReadyDto = createCarNotReadyDto(carAprDTO);
            sendCarNotReadyDtoToKafka(carNotReadyDto);
        } else {
            CarReadyDto carReadyDto = createCarReadyDto(carAprDTO);
            sendCarReadyDtoToKafka(carReadyDto);
        }
    }

    private List<String> createCarPartsList(CarAprDTO carAprDTO) {
        List<String> carPartsList = new ArrayList<>();

        if (!carAprDTO.isBumper()) {
            carPartsList.add("bumper");
        }
        if (!carAprDTO.isWindscreen()) {
            carPartsList.add("windscreen");
        }
        if (!carAprDTO.isClean()) {
            carPartsList.add("clean");
        }
        return carPartsList;
    }

    private CarNotReadyDto createCarNotReadyDto(CarAprDTO carAprDTO) {
        CarNotReadyDto carNotReadyDto = new CarNotReadyDto();
        carNotReadyDto.setNumber(carAprDTO.getNumber());
        carNotReadyDto.setVin(carAprDTO.getVin());
        carNotReadyDto.setBrand(carAprDTO.getBrand());
        carNotReadyDto.setModel(carAprDTO.getModel());
        carNotReadyDto.setBumper(carAprDTO.isBumper());
        carNotReadyDto.setWindscreen(carAprDTO.isWindscreen());
        carNotReadyDto.setClean(carAprDTO.isClean());
        carNotReadyDto.setState(carAprDTO.getState());
        carNotReadyDto.setColor(carAprDTO.getColor());
        carNotReadyDto.setYears(carAprDTO.getYears());
        carNotReadyDto.setMileage(carAprDTO.getMileage());
        carNotReadyDto.setPrice(carAprDTO.getPrice());
        carNotReadyDto.setCarPartsList(createCarPartsList(carAprDTO));
        return carNotReadyDto;
    }

    private CarReadyDto createCarReadyDto(CarAprDTO carAprDTO) {
        CarReadyDto carReadyDto = new CarReadyDto();
        carReadyDto.setNumber(carAprDTO.getNumber());
        carReadyDto.setVin(carAprDTO.getVin());
        carReadyDto.setBrand(carAprDTO.getBrand());
        carReadyDto.setModel(carAprDTO.getModel());
        carReadyDto.setBumper(carAprDTO.isBumper());
        carReadyDto.setWindscreen(carAprDTO.isWindscreen());
        carReadyDto.setClean(carAprDTO.isClean());
        carReadyDto.setState(carAprDTO.getState());
        carReadyDto.setColor(carAprDTO.getColor());
        carReadyDto.setYears(carAprDTO.getYears());
        carReadyDto.setMileage(carAprDTO.getMileage());
        carReadyDto.setPrice(carAprDTO.getPrice());
        return carReadyDto;
    }

    private void sendCarNotReadyDtoToKafka(CarNotReadyDto carNotReadyDto) {
        try {
            String carNotReadyDtoJson = objectMapper.writeValueAsString(carNotReadyDto);
            kafkaTemplate.send("car_dto_not_ready", carNotReadyDtoJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void sendCarReadyDtoToKafka(CarReadyDto carReadyDto) {
        try {
            String carReadyDtoJson = objectMapper.writeValueAsString(carReadyDto);
            kafkaTemplate.send("car_dto_ready", carReadyDtoJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
