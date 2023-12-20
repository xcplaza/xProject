package cars_project.service;

import cars_project.dto.CarAprDTO;
import cars_project.dto.CarParts;
import cars_project.dto.CarPrepairDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarPrepairService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarPrepairService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "car_approve_dto_topic", groupId = "myGroup")
    public void processCarAprDTO(ConsumerRecord<String, String> record) {
        // Десериализация объекта CarAprDTO из строки
        CarAprDTO carAprDTO;
        try {
            carAprDTO = objectMapper.readValue(record.value(), CarAprDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing CarAprDTO", e);
        }

        List<CarParts> carParts = new ArrayList<>();
        if (!carAprDTO.isBumper()) {
            carParts.add(new CarParts("Bumper"));
        }
        if (!carAprDTO.isWindscreen()) {
            carParts.add(new CarParts("Windscreen"));
        }

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
        carPrepairDTO.setCarPartsList(carParts);

        try {
            // Преобразование объекта CarPrepairDto в JSON-строку
            String carPrepairJson = objectMapper.writeValueAsString(carPrepairDTO);

            // Отправка в Kafka
            kafkaTemplate.send("car_dto_ready", carPrepairJson);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing CarPrepairDto", e);
        }
    }
}
