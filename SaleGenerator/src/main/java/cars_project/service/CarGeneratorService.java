package cars_project.service;

import cars_project.entites.Car;

import cars_project.entites.Sale;

import cars_project.repo.CarRepository;
import cars_project.repo.SaleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import java.util.stream.Collectors;

@Service
public class CarGeneratorService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private SaleRepo saleRepo;

    private static final Logger logger = LoggerFactory.getLogger(CarGeneratorService.class);


    @Transactional
    @Scheduled(fixedRate = 20000)
    public void carSales() {
        logger.info("nihua");
        int randomCount = new Random().nextInt(3) + 1;
        logger.info("Random count: {}", randomCount);
        List<Car> cars = carRepository.findRandomCars(randomCount);
        logger.info("Selected cars for deletion: {}", cars);
        if (!cars.isEmpty()) {
            List<Sale> sales = cars.stream().map(this::createSaleFromCar).collect(Collectors.toList());
            saleRepo.saveAll(sales);
            logger.info("Saved sales to the database");
            carRepository.deleteAllInBatch(cars);

        } else {
            logger.info("No cars to delete");

        }
        logger.info("Scheduled task completed");
    }

    private Sale createSaleFromCar(Car car) {
        Sale sale = new Sale();
        sale.setBrand(car.getBrand());
        sale.setBumper(car.isBumper());
        sale.setClean(car.isClean());
        sale.setColor(car.getColor());
        sale.setCreatedAt(LocalDateTime.now());
        sale.setDiscount(5);
        sale.setFinalPrice((int) (car.getPrice()*1.2));
        sale.setMileage(car.getMileage());
        sale.setModel(car.getModel());
        sale.setNumber(car.getNumber());
        sale.setState(car.getState());
        sale.setVin(car.getVin());
        sale.setWindscreen(car.isWindscreen());
        sale.setYears(car.getYears());

        sale.setPrice(car.getPrice());

        return sale;
    }


}
//VALUES ('[123456]','[Toyouta1]','[true]','[List]','[True]','[Green]','[22000]','[Yarus]','[12009]','[56]','[3456HHHHHHHHH]','[true]','[2012]')
