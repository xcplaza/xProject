package MiddleQuality.RandomCars.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {
    public int generateRandomNumber() {
        return new Random().nextInt(900000) + 100000;
    }

    public String generateRandomVin() {
        // return String.valueOf(new Random().nextLong()).substring(0, 17);
        Random random = new Random();
        StringBuilder sb = new StringBuilder(17);
        for (int i = 0; i < 17; i++) {
            char randomChar = (char) (random.nextInt(10) + '0'); // Цифры '0'-'9'
            if (random.nextBoolean()) {
                randomChar = (char) (random.nextInt(26) + 'A'); // Заглавные буквы 'A'-'Z'
            }
            sb.append(randomChar);
        }
        return sb.toString();
    }


    public boolean generateRandomBoolean() {
        return new Random().nextBoolean();
    }

    public int generateRandomState() {
        Random random = new Random();
        return random.nextInt(100);
    }
    public  String[] generateRandomCarModel() {
        String[] carBrands = {"BMW", "Mercedes", "Audi", "Lada", "Hyundai"};
        String[][] carModels = {
                {"X2", "X3", "X5", "X6"},
                {"A-Class", "C-Class", "E-Class", "S-Class"},
                {"A3", "A4", "A6", "Q5"},
                {"Kalina", "XRay", "Niva"},
                {"Accent", "i30", "i20"}
        };

        Random random = new Random();
        int brandIndex = random.nextInt(carBrands.length);
        int modelIndex = random.nextInt(carModels[brandIndex].length);

        String selectedBrand = carBrands[brandIndex];
        String selectedModel = carModels[brandIndex][modelIndex];

        return new String[]{selectedBrand, selectedModel};
    }
}