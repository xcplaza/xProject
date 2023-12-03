package MiddleQuality.RandomCars.repos;


import MiddleQuality.RandomCars.entites.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    }

