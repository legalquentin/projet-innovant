package com.etna.Service;

import com.etna.Entity.Application;
import com.etna.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    private static final Logger logger = Logger.getLogger(ConnectionService.class.getName());

    public Application findUserInOfferApplication(String offer_uuid, String user_uuid) {
        try {
            Iterable<Application> iterable = applicationRepository.findByUserUuid(user_uuid);
            logger.info("iterable count : "+iterable);
            for (Application application : iterable) {
                logger.info("application ID  : "+application.getId());
                if (application.getOffer_uuid() == offer_uuid)
                    return application;
            }
            return null;
        }  catch (Exception e) {
            e.printStackTrace();
           return null;
        }
    }


    public void addApplicant(String offer_uuid, String user_uuid, String status, String notification_uuid) {
        Application application = new Application(offer_uuid, user_uuid, status, notification_uuid);
        applicationRepository.save(application);
    }
}
