package cars_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleGeneratorAppl {
    public static void main(String[] args) {

        SpringApplication.run(SaleGeneratorAppl.class, args);
    }
}
