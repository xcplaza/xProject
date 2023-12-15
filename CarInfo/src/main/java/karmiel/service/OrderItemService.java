package karmiel.service;

import karmiel.entity.OrderItemEntity;
import karmiel.repo.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItemEntity> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    // Другие методы, если необходимо
}
