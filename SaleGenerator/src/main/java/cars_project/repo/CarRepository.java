package cars_project.repo;

import cars_project.entites.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query(value = "SELECT * FROM car ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Car> findRandomCars(int limit);
}
