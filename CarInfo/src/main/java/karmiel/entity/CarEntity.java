package karmiel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auto")
@Data
public class CarEntity {
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
}
