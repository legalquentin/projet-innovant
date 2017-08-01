package com.etna.Service;

import com.etna.Controller.OfferController;
import com.etna.Entity.Experience;
import com.etna.Entity.Offer;
import com.etna.Entity.Trip;
import com.etna.Entity.User;
import com.etna.Repository.ExperienceRepository;
import com.etna.Repository.OfferRepository;
import com.etna.Repository.TripRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(OfferController.class.getName());


    public JSONArray getTripExperiences(String uuid) {
        JSONArray array = new JSONArray();
        for (Experience item : experienceRepository.findByTripUuid(uuid)) {
            array.put(item.recoverJsonData());
        }
        return array;
    }

    public ResponseEntity<Object> AddExperience(Experience experience, String sessionId) {

        if (experience.getContent() == null || experience.getTitle() == null)
            return ErrorService.invalidJsonRequest("Experience have empty fields");

        Trip trip = tripRepository.findByUuid(experience.getTrip_uuid());
        if (trip == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\":\"FORBIDDEN : trip does not exist\"}");
        Offer offer = offerRepository.findByUuid(experience.getOffer_uuid());
        if (offer == null && !Objects.equals(experience.getOffer_uuid(), "none"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\":\"FORBIDDEN : offer does not exist\"}");

        User user = userService.getUserByEmail(ConnectionService.getSessionUser(sessionId));
        experience.setOwner(user.getEmail());
        experience.setUuid(UUID.randomUUID().toString());
        experienceRepository.save(experience);
        return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"OK : experience saved on :"+trip.getName()+"\"}");
    }
}
