package com.example.demo.repository;

import com.example.demo.models.appointment.Appointment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

    /**
     * Retrieve collection of {@link Appointment} from the data store by vet id.
     * @param vetId the vet id to search for
     * @return collection of {@link Appointment} if found
     */
    @Query(value = "SELECT a FROM Appointment a WHERE a.vetId =:vetId")
    @Transactional(readOnly = true)
    List<Appointment> findAppointmentByVetId(@Param("vetId") Integer vetId);

    /**
     * Retrieve collection of {@link Appointment} from the data store by pet id.
     * @param petId the vet id to search for
     * @return collection of {@link Appointment} if found
     */
    @Query(value = "SELECT a FROM Appointment a WHERE a.petId =:petId")
    @Transactional(readOnly = true)
    List<Appointment> findAppointmentByPetId(@Param("petId") Integer petId);

    /**
     * Retrieves collection {@link Appointment} from the data store between two timestamps.
     * @param start timestamp to start search
     * @param end timestamp to end search
     * @return collection of booked {@link Appointment} if found
     */
    @Query(value = "SELECT a FROM Appointment a WHERE a.timeSlot >:start and a.timeSlot <:end")
    List<Appointment> findAppointmentsByTimeSlot(@Param("start") String start, @Param("end") String end);

    /**
     * Retrieves collection {@link Appointment} according to membership.
     * @param vetId vetId to search against
     * @param petId petId to search against
     * @param ownerId ownerId to search against
     * @return collection of booked {@link Appointment} if found
     */
    @Query(value = "SELECT a FROM Appointment a WHERE a.vetId =:vetId or a.petId =:petId or a.ownerId =:ownerId")
    List<Appointment> findAppointmentsByMembers(@Param("vetId") Integer vetId, @Param("petId") Integer petId,
                                                @Param("ownerId") Integer ownerId);

    /**
     * Retrieve an {@link Appointment} from the data store by id.
     * @param id the id to search for
     * @return the {@link Appointment} if found
     */
    @Query(value = "SELECT a FROM Appointment a WHERE a.id =:id")
    @Transactional(readOnly = true)
    Optional<Appointment> findAppointmentById(@Param("id") Integer id);

    /**
     * Retrieve an {@link Appointment} from the data store by id.
     * @param id the id to search for
     * @return the {@link Appointment} if found
     */
    @Query(value = "UPDATE Appointment a SET a.canceled = true WHERE a.id =:id")
    @Modifying
    @Transactional()
    void cancelAppointment(@Param("id") Integer id);

}
