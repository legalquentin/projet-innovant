package com.etna.Repository;

import com.etna.Entity.Experience;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

public interface ExperienceRepository extends CrudRepository<Experience, Integer> {

    @Query(value = "SELECT * FROM experience WHERE trip_uuid = :trip_uuid", nativeQuery = true)
    Iterable<Experience> findByTripUuid(@Param("trip_uuid") String trip_uuid);

    @Query(value = "SELECT * FROM experience WHERE uuid = :uuid", nativeQuery = true)
    Experience findByUuid(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM experience WHERE uuid = :uuid", nativeQuery = true)
    Iterable<Experience> findByUser(String email);
}
