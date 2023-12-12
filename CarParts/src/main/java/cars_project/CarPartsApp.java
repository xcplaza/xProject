package cars_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@SpringBootApplication
public class CarPartsApp {
    public static void main(String[] args) {
        SpringApplication.run(CarPartsApp.class, args);
    }
}
