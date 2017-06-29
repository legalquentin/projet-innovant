package com.etna.Repository;

import com.etna.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by quentin on 29/06/2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM User where email = :id", nativeQuery=true)
    User findByEmail(@Param("id") String id);

}
