package karmiel.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Sale() {
    }

    public Sale(int number, String vin, String brand, String model, boolean bumper, boolean windscreen,
                boolean clean, int state, String color, int years, int mileage, int price) {
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
