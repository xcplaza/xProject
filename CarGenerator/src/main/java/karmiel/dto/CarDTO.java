package karmiel.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "auto") // Указываем имя таблицы
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
    private int years;
    private int mileage;
    private int price;

    public CarDTO(final int number, final String vin, final String brand, final String model, final boolean bumper, final boolean windscreen, final boolean clean, final int state, String color, int years, int mileage, int price) {
        this.number = number;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.bumper = bumper;
        this.windscreen = windscreen;
        this.clean = clean;
        this.state = state;
        this.color = color;
        this.years = years;
        this.mileage = mileage;
        this.price = price;
    }
}
