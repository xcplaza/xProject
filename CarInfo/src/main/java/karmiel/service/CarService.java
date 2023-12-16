package karmiel.service;

import karmiel.entity.CarEntity;

import karmiel.repo.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    @Qualifier("firstDataSource")
    private CarRepo carRepo;

    public List<CarEntity> getAllCars() {
        return carRepo.findAll();
    }

    public Optional<CarEntity> getCarById(Long id) {
        return carRepo.findById(id);
    }
}