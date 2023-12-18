package karmiel.repo;

import karmiel.entity.Auto;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface AutoRepo extends CrudRepository<Auto, Long> {
    Iterable<Auto> findAll(Sort id);
}
