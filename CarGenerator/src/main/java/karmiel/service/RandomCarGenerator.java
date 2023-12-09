package karmiel.service;

import java.util.Random;

public class RandomCarGenerator {
    public int generateRandomNumber() {
        return new Random().nextInt(900000) + 100000;
    }

    public String generateRandomVin() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(17);
        for (int i = 0; i < 17; i++) {
            char randomChar;
            if (random.nextBoolean()) {
                randomChar = (char) (random.nextInt(26) + 'A'); // Заглавные буквы 'A'-'Z'
            } else {
                randomChar = (char) (random.nextInt(10) + '0'); // Цифры '0'-'9'
            }
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
