package com.example.demo.repository;

import com.example.demo.models.vet.Vet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VetRepository extends CrudRepository<Vet, Integer> {
    /**
     * Retrieve an {@link Vet} from the data store by id.
     * @param id the id to search for
     * @return the {@link Vet} if found
     */
    @Query(value = "SELECT v FROM Vet v WHERE v.id =:id")
    @Transactional(readOnly = true)
    Optional<Vet> findVetById(@Param("id") Integer id);


    @Query(value = "SELECT v FROM Vet v WHERE v.phoneNumber =:phoneNumber")
    @Transactional(readOnly = true)
    Optional<Vet> findVetByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
