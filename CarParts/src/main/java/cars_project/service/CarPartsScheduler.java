package cars_project.service;

import cars_project.dto.CarPart;
import cars_project.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CarPartsScheduler {

    @Autowired
    private CarPartsService carPartsService;

    @Scheduled(fixedRate = 10000) // каждые 5 минут
    public void checkAndOrderParts() {
        List<CarPart> lowQuantityParts = carPartsService.getLowQuantityParts(5);

        for (CarPart part : lowQuantityParts) {
            // Формирование заказа
            Order order = new Order(new Date(), "ORDER_NUMBER_HERE");
            order.addOrderItem(part.getType(), 10, part.getQuantity());  // используем метод addOrderItem

            // Увеличение количества позиций и сохранение в БД
            part.setQuantity(10);
            carPartsService.saveCarPart(part);

            // Сохранение заказа в БД
            carPartsService.saveOrder(order);
        }
    }
}


