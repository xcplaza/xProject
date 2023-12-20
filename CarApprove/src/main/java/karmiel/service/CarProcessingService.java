package karmiel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(CarProcessingService.CarProcessor.class)
public class CarProcessingService {

    @Autowired
    private CarProcessor processor;

    @StreamListener(CarProcessor.INPUT)
    public void processCar(CarDTO carDTO) {
        try {
            if (carDTO.getState() > 50) {
                processor.outputApproval().send(MessageBuilder.withPayload(convertToCarAprDTO(carDTO)).build());
            } else {
                processor.outputRejection().send(MessageBuilder.withPayload(convertToCarRejDTO(carDTO)).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CarAprDTO convertToCarAprDTO(CarDTO carDTO) {
        return new CarAprDTO(
                carDTO.getNumber(), carDTO.getVin(), carDTO.getBrand(), carDTO.getModel(),
                carDTO.isBumper(), carDTO.isWindscreen(), carDTO.isClean(), carDTO.getState(),
                carDTO.getColor(), carDTO.getYears(), carDTO.getMileage(), carDTO.getPrice()
        );
    }

    private CarRejDTO convertToCarRejDTO(CarDTO carDTO) {
        return new CarRejDTO(
                carDTO.getNumber(), carDTO.getVin(), carDTO.getBrand(), carDTO.getModel(),
                carDTO.isBumper(), carDTO.isWindscreen(), carDTO.isClean(), carDTO.getState(),
                carDTO.getColor(), carDTO.getYears(), carDTO.getMileage(), carDTO.getPrice()
        );
    }

    interface CarProcessor {
        @Input
        SubscribableChannel input();

        @Output
        MessageChannel outputApproval();

        @Output
        MessageChannel outputRejection();
    }
}