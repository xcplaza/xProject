package karmiel.repo;

import karmiel.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
}