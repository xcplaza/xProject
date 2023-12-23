package cars_project.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Data
public class Sale {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private int number;
        private String vin;
        private String brand;
        private String model;
        private boolean bumper;
        private boolean windscreen;
        private boolean clean;
        private int state;
        private String color;
        private int years;
        private int mileage;
        private int price;

        private int finalPrice;
        private int discount;
        @Column(columnDefinition = "TIMESTAMP")
        private LocalDateTime createdAt;
    }

