package karmiel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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

    public Auto(final int number, final String vin, final String brand, final String model, final boolean bumper, final boolean windscreen, final boolean clean, final int state, final String color, final int years, final int mileage, final int price) {
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
