package com.etna.Service;

import com.etna.Entity.Application;
import com.etna.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application findUserInOfferApplication(String offer_uuid, String user_uuid) {
        return applicationRepository.findByUserOfferUuid(offer_uuid, user_uuid);
    }

    public void addApplicant(String offer_uuid, String user_uuid, String status, String notification_uuid) {
        Application application = new Application(offer_uuid, user_uuid, status, notification_uuid);
        applicationRepository.save(application);
    }
}
