package karmiel.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class CarDTO {
    @Id
    private int number;
    private String vin;
    private String brand;
    private String model;
    private boolean bumper;
    private boolean windscreen;
    private boolean clean;
    private int state;
    private String color;
    private int year;

    public CarDTO(final int number, final String vin, final String brand, final String model, final boolean bumper, final boolean windscreen, final boolean clean, final int state, String color, int year) {
        this.number = number;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.bumper = bumper;
        this.windscreen = windscreen;
        this.clean = clean;
        this.state = state;
        this.color = color;
        this.year = year;
    }
}
