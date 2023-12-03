package karmiel.controllers;

import karmiel.entity.RandomCar;
import karmiel.repo.RandomCarRepo;
import karmiel.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/cars")
public class RandomController {


    @Autowired
    RandomService carService;
    @Autowired
    RandomCarRepo randomCarRepo;

//        public Controller(RandomService carService){
//        this.carService=carService;
//        }

    @GetMapping("/generateRandom")
    public List<RandomCar> generateRandomCars() {
        int numberOfCars = new Random().nextInt(16);
        List<RandomCar> randomCars = new ArrayList<>();

        for (int i = 0; i < numberOfCars; i++) {
            RandomCar randomCar = new RandomCar();
            randomCar.setNumber(carService.generateRandomNumber());
            randomCar.setVin(carService.generateRandomVin());
            // car.setBrand(getBrandFromDatabase());
            //car.setModel(getModelFromDatabase());
            randomCar.setBumper(carService.generateRandomBoolean());
            randomCar.setWindscreen(carService.generateRandomBoolean());
            randomCar.setClean(carService.generateRandomBoolean());
            randomCar.setRepair(carService.generateRandomBoolean());
            randomCars.add(randomCarRepo.save(randomCar));
        }
        return randomCars;
    }

}
