package com.example.demo.repository;

import com.example.demo.models.owner.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    /**
     * Retrieve an {@link Owner} from the data store by phone number.
     * @param phoneNumber the phoneNumber to search for
     * @return the {@link Owner} if found
     */

    @Query(value = "SELECT o FROM Owner o where o.phoneNumber = :phoneNumber")
    @Transactional(readOnly = true)
    Owner findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * Retrieve an {@link Owner} from the data store by id.
     * @param id the id to search for
     * @return the {@link Owner} if found
     */
    @Query(value = "SELECT o FROM Owner o WHERE o.id =:id")
    @Transactional(readOnly = true)
    Optional<Owner> findById(@Param("id") Integer id);

}
