package karmiel.repo;

import karmiel.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItemEntity, Long> {
}