package com.example.demo.repository;

import com.example.demo.models.vet.Vet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VetRepository extends CrudRepository<Vet, Integer> {
    /**
     * Retrieve an {@link Vet} from the data store by id.
     * @param id the id to search for
     * @return the {@link Vet} if found
     */
    @Query(value = "SELECT * FROM vets WHERE vet.id =:id", nativeQuery = true)
    @Transactional(readOnly = true)
    Vet findVetById(@Param("id") Integer id);

}
