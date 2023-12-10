package karmiel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
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
}
