package karmiel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarSaleApp {
    public static void main(String[] args) {
        SpringApplication.run(CarSaleApp.class, args);
    }
}
