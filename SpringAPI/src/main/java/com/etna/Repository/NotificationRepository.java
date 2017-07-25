package com.etna.Repository;

import com.etna.Entity.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by quentin on 21/07/2017.
 */
public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    @Query(value = "SELECT * FROM notification where uuid = :uuid", nativeQuery = true)
    Notification findByUuid(@Param("uuid") String uuid);

    @Query(value = "SELECT * FROM notification where recipient = :recipient", nativeQuery = true)
    Iterable<Notification> findByRecipient(@Param("recipient") String recipient);

    @Query(value = "SELECT * FROM notification where sender = :sender", nativeQuery = true)
    Iterable<Notification> findBySender(@Param("sender") String sender);

}
