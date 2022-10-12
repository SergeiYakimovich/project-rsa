package code.repository;

import code.model.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRepository extends CrudRepository<Work, Long> {
    Optional<Work> findById(Long id);
    List<Work> findAll();
    List<Work> findAllByOrderId(Long id);
    List<Work> findAllByOrderName(String name);
}
