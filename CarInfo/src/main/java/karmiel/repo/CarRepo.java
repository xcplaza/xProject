package karmiel.repo;

import karmiel.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CarRepo extends JpaRepository<CarEntity, Long> {
    @Query(value = "SELECT * FROM auto", nativeQuery = true)
    List<CarEntity> findAllCarsFromFirstDatabase();
}