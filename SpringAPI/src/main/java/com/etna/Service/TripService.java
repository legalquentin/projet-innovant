package com.etna.Service;

import com.etna.Entity.Trip;
import com.etna.Entity.User;
import com.etna.Repository.TripRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ExperienceService experienceService;

    public ResponseEntity<Object> getAllTrips(String sessionId) {
        try {
            User user = userService.getUserByEmail(ConnectionService.getSessionUser(sessionId));
            JSONArray contacts = contactService.getUserContacts(user);
            List<String> contacts_list = new ArrayList<String>();
            for(int i = 0 ; i < contacts.length() ; i++){
                contacts_list.add(contacts.getJSONObject(i).getString("friend_uuid"));
            }
            if (user == null)
                return ErrorService.standardError("user not found");
            JSONArray user_trips = new JSONArray();

            /* Check if the trip is from the user or one of his contacts */

            for (Trip trip : tripRepository.findAll()) {
                if (Objects.equals(trip.getOwner_uuid(), user.getUuid()) || contacts_list.contains(trip.getOwner_uuid())) {
                    user_trips.put(trip.recoverJsonData());
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(user_trips.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorService.standardError(e.getMessage());
        }
    }

    public ResponseEntity<Object> insertTrip(Trip trip, String sessionId) {
        User user = userService.getUserByEmail(ConnectionService.getSessionUser(sessionId));

        if (trip.getName() == null)
            return ErrorService.invalidJsonRequest("trip name is not defined ");
        trip.setOwner(user.getEmail());
        trip.setUuid(UUID.randomUUID().toString());
        trip.setOwner_uuid(user.getUuid());
        trip.setId(null);
        for (Trip tmp : tripRepository.findByOwner(user.getEmail())) {
            if (tmp.getName() == trip.getName())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\":\"FORBIDDEN : trip already exist\"}");
        }
        tripRepository.save(trip);
        return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"OK : trip saved\"}");
    }

    public ResponseEntity<Object> getAllTripExperience(String uuid, String sessionId) {
        Trip trip = tripRepository.findByUuid(uuid);
        if (trip == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\":\"FORBIDDEN : trip does not exist\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(experienceService.getTripExperiences(trip.getUuid()).toString());
    }
}
