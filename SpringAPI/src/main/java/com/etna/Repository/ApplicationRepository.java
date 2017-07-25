package com.etna.Repository;

import com.etna.Entity.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {

    @Query(value = "SELECT * FROM application WHERE user_uuid = :user_uuid", nativeQuery = true)
    Iterable<Application> findByUserUuid(@Param("user_uuid") String user_uuid);

    @Query(value = "SELECT * FROM application WHERE offer_uuid = :offer_uuid", nativeQuery = true)
    Iterable<Application> findByOfferUuid(@Param("offer_uuid") String offer_uuid);

    @Query(value = "SELECT * FROM application WHERE notification_uuid = :notification_uuid", nativeQuery = true)
    Iterable<Application> findByNotificationUuid(@Param("notification_uuid") String notification_uuid);

    @Query(value = "SELECT * FROM application WHERE state = :state", nativeQuery = true)
    Iterable<Application> findByState(@Param("state") String state);

    @Query(value = "SELECT * FROM application WHERE offer_uuid = :offer_uuid AND user_uuid = :user_uuid" , nativeQuery = true)
    Application findByUserOfferUuid(@Param("offer_uuid") String offer_uuid, @Param("user_uuid") String user_uuid);

    @Query(value = "SELECT * FROM Applications WHERE state = :state AND user_uuid = :user_uuid" , nativeQuery = true)
    Iterable<Application> findByStateUserUuid(@Param("state") String state, @Param("user_uuid") String user_uuid);
}
