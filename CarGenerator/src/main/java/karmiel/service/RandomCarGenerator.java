package karmiel.service;

import java.time.LocalDate;
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

    public int generateRandomYear() {
        int currentYear = LocalDate.now().getYear();
        int startYear = 1950;
        return (int) (Math.random() * (currentYear - startYear + 1)) + startYear;
    }

    public int generateRandomMileage() {
        return (int) (Math.random() * 300001); // Генерация пробега от 0 до 300,000 км
    }

    public int calculateState(int year, int mileage) {
        // Логика расчета состояния в зависимости от года выпуска и пробега
        int baseState = 100; // Максимальное состояние

        // Чем старше год выпуска и больше пробег - тем ниже состояние
        int agePenalty = Math.max(0, LocalDate.now().getYear() - year); // баллы за возраст
        int mileagePenalty = Math.min(mileage / 10000, 50); // баллы за пробег (максимум 50)

        // Уменьшаем баллы, если он приводит к отрицательному результату
        agePenalty = Math.min(agePenalty, baseState);
        mileagePenalty = Math.min(mileagePenalty, baseState);

        return baseState - agePenalty - mileagePenalty;
    }

    public String generateRandomColor() {
        String[] colors = {"Red", "Blue", "Green", "White", "Black", "Silver", "Yellow", "Orange", "Purple"};
        int randomIndex = (int) (Math.random() * colors.length);
        return colors[randomIndex];
    }

    public int generateRandomPrice(int year, int mileage, int state) {
        // Логика генерации цены в зависимости от года, пробега и состояния авто
        // чем старше год, больше пробег и ниже состояние - тем дешевле
        int basePrice = 10000; // Минимальная цена

        int ageBonus = Math.max(0, year - 2000); // бонус за возраст (год выпуска после 2000)
        int mileageBonus = Math.max(0, 300000 - mileage) / 10000; // бонус за пробег (максимум 30,000 км)
        int statePenalty = Math.max(0, 100 - state); // баллы за состояние

        return basePrice + ageBonus + mileageBonus - statePenalty;
    }
}
