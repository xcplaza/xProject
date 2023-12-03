package karmiel.repo;

import karmiel.entity.RandomCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RandomCarRepo extends CrudRepository<RandomCar, Long> {
}

