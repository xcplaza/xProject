package karmiel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
//    @Column(name = "number", nullable = false, columnDefinition = "INT DEFAULT 0")
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
    @Column(name = "view", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int view;
}

