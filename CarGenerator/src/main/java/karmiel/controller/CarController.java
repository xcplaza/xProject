package karmiel.controller;


import karmiel.producer.CarProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarProducer carProducer;

    @Autowired
    public CarController(CarProducer carProducer) {
        this.carProducer = carProducer;
    }

    @GetMapping("/generate")
    public String generateAndSendCar() {
        carProducer.generateAndSendCar();
        return "Car generation and sending to Kafka initiated.";
    }
}

