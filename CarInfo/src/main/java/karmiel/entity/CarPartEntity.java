package karmiel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "car_part")
@Data
public class CarPartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String type;
}