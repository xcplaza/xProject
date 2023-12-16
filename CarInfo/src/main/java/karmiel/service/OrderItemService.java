package karmiel.service;

import karmiel.entity.OrderItemEntity;
import karmiel.repo.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepo;

    public List<OrderItemEntity> getAllOrderItems() {
        return orderItemRepo.findAll();
    }

    public Optional<OrderItemEntity> getOrderItemById(Long id) {
        return orderItemRepo.findById(id);
    }

}
