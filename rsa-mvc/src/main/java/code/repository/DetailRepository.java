package code.repository;

import code.model.Detail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailRepository extends CrudRepository<Detail, Long> {
    Optional<Detail> findById(Long id);
    List<Detail> findAll();
    List<Detail> findAllByOrderId(Long id);
    List<Detail> findAllByOrderName(String name);
}
