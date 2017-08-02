package com.etna.Repository;

import com.etna.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by quentin on 29/06/2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery=true)
    User findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user WHERE uuid = :uuid", nativeQuery=true)
    User findByuuid(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM user WHERE email = :email AND password = :password", nativeQuery=true)
    User authenticate(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE uuid = :uuid", nativeQuery=true)
    Void findByUuid(@Param("uuid") String uuid);

    boolean existsByEmail(String Email);
}
