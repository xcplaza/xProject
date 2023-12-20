package cars_project.service;


import cars_project.dto.CarAprDTO;
import cars_project.dto.CarParts;
import cars_project.dto.CarPrepairDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarPrepairService {

    private final KafkaTemplate<String, CarPrepairDto> kafkaTemplate;

    @Autowired
    public CarPrepairService(KafkaTemplate<String, CarPrepairDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "car_approve_dto_topic", groupId = "myGroup")
    public void processCarAprDTO(ConsumerRecord<String, CarAprDTO> record) {
        CarAprDTO carAprDTO = record.value();

        List<CarParts> carParts = new ArrayList<>();
        if (!carAprDTO.isBumper()) {
            carParts.add(new CarParts("Bumper"));
        }
        if (!carAprDTO.isWindscreen()) {
            carParts.add(new CarParts("Windscreen"));
        }

        // Формирование нового CarPrepairDTO
        CarPrepairDto carPrepairDTO = new CarPrepairDto();
        carPrepairDTO.setNumber(carAprDTO.getNumber());
        carPrepairDTO.setVin(carAprDTO.getVin());
        carPrepairDTO.setBrand(carAprDTO.getBrand());
        carPrepairDTO.setModel(carAprDTO.getModel());
        carPrepairDTO.setBumper(carAprDTO.isBumper());
        carPrepairDTO.setWindscreen(carAprDTO.isWindscreen());
        carPrepairDTO.setClean(carAprDTO.isClean());
        carPrepairDTO.setState(carAprDTO.getState());
        carPrepairDTO.setColor(carPrepairDTO.getColor());
        carPrepairDTO.setYears(carAprDTO.getYears());
        carPrepairDTO.setMileage(carPrepairDTO.getMileage());
        carPrepairDTO.setPrice(carPrepairDTO.getPrice());
//            carPrepairDTO.setArpooved(carAprDTO.isArpooved());
        carPrepairDTO.setCarPartsList(carParts);

        kafkaTemplate.send("car_dto_ready", carPrepairDTO);
    }
}


