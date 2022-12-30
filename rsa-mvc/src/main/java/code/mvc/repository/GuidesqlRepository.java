package code.mvc.repository;

import code.mvc.model.Guidesql;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuidesqlRepository extends CrudRepository<Guidesql, Long> {
    List<Guidesql> findAll();
    Optional<Guidesql> findById(Long id);
    Optional<Guidesql> findByCarName(String name);
}