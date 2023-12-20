package karmiel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Car {
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
    private List<String> carPartsList;

    public List<String> getCarPartsList() {
        return carPartsList;
    }

    public void setCarPartsList(List<String> carPartsList) {
        this.carPartsList = carPartsList;
    }
}

