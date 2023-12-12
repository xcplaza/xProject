package cars_project.service;

import cars_project.dto.CarPart;
import cars_project.dto.Order;
import cars_project.repo.CarPartsRepository;
import cars_project.repo.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static cars_project.service.CarPartsService.OrderNumberGenerator.generateOrderNumber;

@Service
public class CarPartsService {

    @Autowired
    private CarPartsRepository carPartsRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<CarPart> getLowQuantityParts(int threshold) {
        return carPartsRepository.findByQuantityLessThan(threshold);
    }

    @Transactional
    public void saveCarPart(CarPart carPart) {
        carPartsRepository.save(carPart);
    }

    @Transactional
    public void saveOrder(Order order) {
        order.setOrderDate(new Date()); // Установите корректную дату
        order.setOrderNumber(generateOrderNumber()); // Реализуйте метод генерации номера заказа
        orderRepository.save(order);
    }

    public class OrderNumberGenerator {

        public static String generateOrderNumber() {
            // Формат даты для включения в номер заказа
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            // Генерация случайного числа для включения в номер заказа
            Random random = new Random();
            int randomSuffix = random.nextInt(10000); // Максимальное четырехзначное число

            // Создание уникального номера заказа на основе даты и случайного числа
            String orderNumber = dateFormat.format(new Date()) + randomSuffix;

            return orderNumber;
        }

        public static void main(String[] args) {
            String generatedOrderNumber = generateOrderNumber();
            System.out.println("Generated Order Number: " + generatedOrderNumber);
        }
    }

    public Order calculateOrderDetails(CarPart carPart) {
        int currentQuantity = carPart.getQuantity();
        int maxQuantity = 10; // Максимальное допустимое количество

        if (currentQuantity < maxQuantity) {
            int orderQuantity = maxQuantity - currentQuantity;

            // Создание заказа
            Order order = new Order();
            order.setOrderDate(new Date());
            order.setOrderNumber(generateOrderNumber());
            order.addOrderItem(carPart.getType(), maxQuantity, orderQuantity);

            // Установка нового количества детали в базе данных
            carPart.setQuantity(maxQuantity);
            saveCarPart(carPart);

            return order;
        }

        return null; // Нет необходимости в заказе
    }
}

