package cars_project.repo;

import cars_project.dto.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarPartsRepository extends JpaRepository<CarPart, Long> {
    List<CarPart> findByQuantityLessThan(int quantity);
}

