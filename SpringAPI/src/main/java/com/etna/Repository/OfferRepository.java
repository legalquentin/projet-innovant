package com.etna.Repository;

import com.etna.Entity.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by quentin on 29/06/2017.
 */
public interface OfferRepository extends CrudRepository<Offer, Integer> {

    @Query(value = "SELECT * FROM offer where uuid = :uuid", nativeQuery = true)
    Offer findByUuid(@Param("uuid") String uuid);

}
