package uz.kassa.microservice.saga.model.repository;

import org.springframework.data.repository.CrudRepository;
import uz.kassa.microservice.saga.model.domain.SagaEntry;

import java.util.Optional;

/**
 * @author Tadjiev Dilshod
 */
public interface SagaRepository extends CrudRepository<SagaEntry, String> {
    Optional<SagaEntry> findByIdAndSagaType(String id,String sagaType);
}
