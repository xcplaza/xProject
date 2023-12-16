package karmiel.repo;

import karmiel.entity.CarPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPartRepo extends JpaRepository<CarPartEntity, Long> {
}