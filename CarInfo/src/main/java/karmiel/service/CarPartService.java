package karmiel.service;

import karmiel.entity.CarPartEntity;
import karmiel.repo.CarPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CarPartService {

    @Autowired
    private CarPartRepository carPartRepository;

    public List<CarPartEntity> getAllCarParts() {
        return carPartRepository.findAll();
    }

    public Optional<CarPartEntity> getCarPartById(Long id) {
        return carPartRepository.findById(id);
    }

    // Другие методы, если необходимо
}
