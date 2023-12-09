package karmiel.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import karmiel.dto.CarAprDTO;
import karmiel.dto.CarDTO;
import karmiel.dto.CarRejDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import karmiel.kafka.KafkaProducer;

@Service
public class CarProcessingService {
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarProcessingService(KafkaProducer kafkaProducer, ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    public void processCar(String carDTOAsString) {
        try {
            // Преобразование строки в объект CarDTO
            CarDTO carDTO = objectMapper.readValue(carDTOAsString, CarDTO.class);

            if ((carDTO.isBumper() || carDTO.isWindscreen() || carDTO.isClean())
                    && (carDTO.getState() < 50 || (carDTO.getState() < 60 && !carDTO.isBumper() && !carDTO.isWindscreen() && !carDTO.isClean()))) {
                // Отклонение автомобиля
                CarRejDTO carRejDTO = createRejectionDTO(carDTO);
                kafkaProducer.sendRejectionMessage(carRejDTO);
            } else {
                // Утверждение автомобиля
                CarAprDTO carAprDTO = createApprovalDTO(carDTO);
                kafkaProducer.sendApprovalMessage(carAprDTO);
            }
        } catch (Exception e) {
            // Обработка ошибок, если не удалось отправить сообщение в Kafka
            e.printStackTrace();
        }
    }

    private CarRejDTO createRejectionDTO(CarDTO carDTO) {
        return new CarRejDTO(
                carDTO.getNumber(),
                carDTO.getVin(),
                carDTO.getBrand(),
                carDTO.getModel(),
                carDTO.isBumper(),
                carDTO.isWindscreen(),
                carDTO.isClean(),
                carDTO.getState()
        );
    }

    private CarAprDTO createApprovalDTO(CarDTO carDTO) {
        return new CarAprDTO(
                carDTO.getNumber(),
                carDTO.getVin(),
                carDTO.getBrand(),
                carDTO.getModel(),
                carDTO.isBumper(),
                carDTO.isWindscreen(),
                carDTO.isClean(),
                carDTO.getState()
        );
    }
}
