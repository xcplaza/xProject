package karmiel.repo;

import karmiel.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<CarEntity, Long> {
}