package karmiel.repo;

import karmiel.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Auto, Long> {
}

