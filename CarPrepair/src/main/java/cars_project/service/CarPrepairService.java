package cars_project.service;

import cars_project.dto.CarAprDTO;
import cars_project.dto.CarParts;
import cars_project.dto.CarPrepairDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarPrepairService {

    private final KafkaTemplate<Object, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarPrepairService(KafkaTemplate<Object, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "car_approve_dto_topic", groupId = "car-prepair")
    public void processCarAprDTO(ConsumerRecord<String, String> record) {
        String carAprDTOString = record.value();

        // Преобразование строки в объект CarAprDTO
        CarAprDTO carAprDTO = convertStringToCarAprDTO(carAprDTOString);

        List<CarParts> carParts = new ArrayList<>();
        if (carAprDTO != null && !carAprDTO.isBumper()) {
            carParts.add(new CarParts("Bumper"));
        }
        if (carAprDTO != null && !carAprDTO.isWindscreen()) {
            carParts.add(new CarParts("Windscreen"));
        }

        // Формирование нового CarPrepairDTO
        CarPrepairDto carPrepairDTO = new CarPrepairDto();
        carPrepairDTO.setNumber(carAprDTO != null ? carAprDTO.getNumber() : null);
        carPrepairDTO.setVin(carAprDTO != null ? carAprDTO.getVin() : null);
        carPrepairDTO.setBrand(carAprDTO != null ? carAprDTO.getBrand() : null);
        carPrepairDTO.setModel(carAprDTO != null ? carAprDTO.getModel() : null);
        carPrepairDTO.setBumper(carAprDTO != null && carAprDTO.isBumper());
        carPrepairDTO.setWindscreen(carAprDTO != null && carAprDTO.isWindscreen());
        carPrepairDTO.setClean(carAprDTO != null && carAprDTO.isClean());
        carPrepairDTO.setState(carAprDTO != null ? carAprDTO.getState() : null);
        carPrepairDTO.setColor(carAprDTO != null ? carAprDTO.getColor() : null);
        carPrepairDTO.setYears(carAprDTO != null ? carAprDTO.getYears() : null);
        carPrepairDTO.setMileage(carAprDTO != null ? carAprDTO.getMileage() : null);
        carPrepairDTO.setPrice(carAprDTO != null ? carAprDTO.getPrice() : null);
//            carPrepairDTO.setArpooved(carAprDTO != null && carAprDTO.isArpooved());
        carPrepairDTO.setCarPartsList(carParts);
        String carPrepairDTOString = convertCarPrepairDtoToString(carPrepairDTO);
        kafkaTemplate.send("car_ready", carPrepairDTOString);
    }

    // Метод для преобразования строки в объект CarAprDTO
    private CarAprDTO convertStringToCarAprDTO(String carAprDTOString) {
        try {
            return objectMapper.readValue(carAprDTOString, CarAprDTO.class);
        } catch (IOException e) {
            e.printStackTrace(); // Обработайте исключение в соответствии с вашей логикой
            return null;
        }
    }

    private String convertCarPrepairDtoToString(CarPrepairDto carPrepairDto) {
        try {
            return objectMapper.writeValueAsString(carPrepairDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception according to your requirements
            return null;
        }
    }
}
