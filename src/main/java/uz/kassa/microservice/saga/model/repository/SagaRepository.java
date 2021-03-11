package uz.kassa.microservice.saga.model.repository;

import org.springframework.data.repository.CrudRepository;
import uz.kassa.microservice.saga.model.domain.SagaEntry;

/**
 * @author Tadjiev Dilshod
 */
public interface SagaRepository extends CrudRepository<SagaEntry, String> {
}
