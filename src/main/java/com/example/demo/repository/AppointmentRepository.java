package com.example.demo.repository;

import com.example.demo.models.appointment.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

    /**
     * Retrieve collection of {@link Appointment} from the data store by vet id.
     * @param vetId the vet id to search for
     * @return collection of {@link Appointment} if found
     */
    @Query(value = "SELECT * FROM appointments WHERE appointments.vet_id =:vet_id", nativeQuery = true)
    @Transactional(readOnly = true)
    List<Appointment> findAppointmentByVetId(@Param("vet_id") Integer vetId);

    /**
     * Retrieve collection of {@link Appointment} from the data store by pet id.
     * @param petId the vet id to search for
     * @return collection of {@link Appointment} if found
     */
    @Query(value = "SELECT * FROM appointments WHERE appointments.pet_id =:pet_id", nativeQuery = true)
    @Transactional(readOnly = true)
    List<Appointment> findAppointmentByPetId(@Param("pet_id") Integer petId);

    /**
     * Retrieves collection {@link Appointment} from the data store between two timestamps.
     * @param start timestamp to start search
     * @param end timestamp to end search
     * @return collection of booked {@link Appointment} if found
     */
    @Query(value = "SELECT * FROM appointments WHERE appointments.timeslot >:start and appointments.timeslot <:end", nativeQuery = true)
    List<Appointment> findAppointmentsByTimeSlot(@Param("start") String start, @Param("end") String end);

    /**
     * Retrieve an {@link Appointment} from the data store by id.
     * @param id the id to search for
     * @return the {@link Appointment} if found
     */
    @Query(value = "SELECT * FROM appointments WHERE appointments.id =:id", nativeQuery = true)
    @Transactional(readOnly = true)
    Appointment findAppointmentById(@Param("id") Integer id);

}
