package karmiel.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String model;
    private String year;
    private String color;
    private String price;
    private int view;

    public Auto(final String name, final String model, final String year, final String color, final String price) {
        this.name = name;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
    }
}
