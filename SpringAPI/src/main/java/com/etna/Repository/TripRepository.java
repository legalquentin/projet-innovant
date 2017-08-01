package com.etna.Repository;

import com.etna.Entity.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends CrudRepository<Trip, Integer> {

    @Query(value = "SELECT * FROM trip WHERE owner = :email", nativeQuery=true)
    Iterable<Trip> findByOwner(@Param("email") String email);


    @Query(value = "SELECT * FROM trip WHERE uuid = :uuid", nativeQuery = true)
    Trip findByUuid(@Param("uuid") String uuid);
}
