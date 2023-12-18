package cars_project.service;


import cars_project.dto.CarAprDTO;
import cars_project.dto.CarParts;
import cars_project.dto.CarPrepairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarPrepairService {

    @Autowired
    private KafkaTemplate<String, CarPrepairDto> kafkaTemplate;
    @KafkaListener(topics = "car_approve_dto_topic", groupId = "myGroup")
    public void consumeCarAprDTO(CarAprDTO carAprDTO) {
        processCarAprDTO(carAprDTO);
    }

    public void processCarAprDTO(CarAprDTO carAprDTO) {
        CarPrepairDto carPrepairDto = createCarPrepairDto(carAprDTO);

        kafkaTemplate.send("car_dto_ready", carPrepairDto);
    }


    private CarPrepairDto createCarPrepairDto(CarAprDTO carAprDTO) {
        CarPrepairDto carPrepairDto = new CarPrepairDto();
        carPrepairDto.setNumber(carAprDTO.getNumber());
        carPrepairDto.setVin(carAprDTO.getVin());
        carPrepairDto.setBrand(carAprDTO.getBrand());
        carPrepairDto.setModel(carAprDTO.getModel());
        carPrepairDto.setBumper(carAprDTO.isBumper());
        carPrepairDto.setWindscreen(carAprDTO.isWindscreen());
        carPrepairDto.setClean(carAprDTO.isClean());
        carPrepairDto.setState(carAprDTO.getState());
        carPrepairDto.setColor(carAprDTO.getColor());
        carPrepairDto.setYears(carAprDTO.getYears());
        carPrepairDto.setMileage(carAprDTO.getMileage());
        carPrepairDto.setPrice(carAprDTO.getPrice());

        carPrepairDto.setCarPartsList(createCarPartsList(carAprDTO));

        return carPrepairDto;
    }

    private List<CarParts> createCarPartsList(CarAprDTO carAprDTO) {
        List<CarParts> carPartsList = new ArrayList<>();

        if (!carAprDTO.isBumper()) {
            carPartsList.add(new CarParts("bumper"));
        }
        if (!carAprDTO.isWindscreen()) {
            carPartsList.add(new CarParts("windscreen"));
        }
        if (!carAprDTO.isClean()) {
            carPartsList.add(new CarParts("clean"));
        }


        return carPartsList;
    }
}

