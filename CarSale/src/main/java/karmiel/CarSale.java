package karmiel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarSale {
    public static void main(String[] args) {
        SpringApplication.run(CarSale.class, args);
    }
}
