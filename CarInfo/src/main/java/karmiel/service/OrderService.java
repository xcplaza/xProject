package karmiel.service;

import karmiel.entity.OrderEntity;
import karmiel.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public List<OrderEntity> getAllOrders() {
        return orderRepo.findAll();
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepo.findById(id);
    }
}
