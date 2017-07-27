package com.etna.Repository;


import com.etna.Entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query(value = "SELECT * FROM message WHERE sender = :email OR recipient = :email", nativeQuery = true)
    Iterable<Message> findUserMails(@Param("email") String email);

}
