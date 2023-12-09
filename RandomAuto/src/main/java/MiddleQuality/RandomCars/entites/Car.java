package MiddleQuality.RandomCars.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private int number;
        @Id
        private String vin;
        private String brand;
        private String model;
        private boolean bumper;
        private boolean windscreen;
        private boolean clean;
        private int state;

}
