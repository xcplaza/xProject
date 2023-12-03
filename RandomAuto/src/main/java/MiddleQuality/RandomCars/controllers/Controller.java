package MiddleQuality.RandomCars.controllers;

import MiddleQuality.RandomCars.entites.Car;
import MiddleQuality.RandomCars.repos.CarRepository;
import MiddleQuality.RandomCars.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RestController
@RequestMapping("/api/cars")
public class Controller {


        @Autowired
         RandomService carService;
    @Autowired
         CarRepository carRepository;

//        public Controller(RandomService carService){
//        this.carService=carService;
//        }




        @GetMapping("/generateRandom")
        public List<Car> generateRandomCars() {
            int numberOfCars = new Random().nextInt(16);
            List<Car> cars = new ArrayList<>();

            for (int i = 0; i < numberOfCars; i++) {
                Car car = new Car();
                car.setNumber(carService.generateRandomNumber());
                car.setVin(carService.generateRandomVin());
               // car.setBrand(getBrandFromDatabase());
                //car.setModel(getModelFromDatabase());
                car.setBumper(carService.generateRandomBoolean());
                car.setWindscreen(carService.generateRandomBoolean());
                car.setClean(carService.generateRandomBoolean());
                car.setRepair(carService.generateRandomBoolean());

                cars.add(carRepository.save(car));
            }

            return cars;
        }

}
