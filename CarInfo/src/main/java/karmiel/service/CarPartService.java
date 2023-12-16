package karmiel.service;

import karmiel.entity.CarPartEntity;
import karmiel.repo.CarPartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CarPartService {

    @Autowired
    private CarPartRepo carPartRepo;

    public List<CarPartEntity> getAllCarParts() {
        return carPartRepo.findAll();
    }

    public Optional<CarPartEntity> getCarPartById(Long id) {
        return carPartRepo.findById(id);
    }

}
