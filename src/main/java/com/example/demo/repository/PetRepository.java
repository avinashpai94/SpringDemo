package com.example.demo.repository;

import com.example.demo.models.pet.Pet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends CrudRepository<Pet, Integer> {

    /**
     * Retrieve a {@link Pet} from the data store by id.
     * @param id the id to search for
     * @return the {@link Pet} if found
     */
    @Query(value = "SELECT p FROM Pet p where p.id = :id")
    @Transactional(readOnly = true)
    Optional<Pet> findById(@Param("id") Integer id);

    /**
     * Retrieve Collection of {@link Pet} from the data store by owner id.
     * @param id the owner id to search for
     * @return Collection of {@link Pet} if found
     */
    @Query(value = "SELECT p FROM Pet p where p.ownerId = :id")
    @Transactional(readOnly = true)
    List<Pet> findByOwnerId(@Param("id") Integer id);

}
