package com.etna.Repository;

import com.etna.Entity.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

    @Query(value = "SELECT friend_uuid FROM contact WHERE user_uuid = :user_uuid", nativeQuery = true)
    Iterable<String> findUserFriends(@Param("user_uuid") String user_uuid);

}
