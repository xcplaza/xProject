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
}
