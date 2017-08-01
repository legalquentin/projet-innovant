package com.etna.Controller;

import com.etna.Entity.Trip;
import com.etna.Service.ConnectionService;
import com.etna.Service.ErrorService;
import com.etna.Service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());

    // GET ALL TRIPS
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllTrips(@RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Trips : getAllTrips requested");
        if (!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return tripService.getAllTrips(sessionId);
    }

    // GET ALL TRIP EXPERIENCES
    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllTripExperiences(@PathVariable("uuid") String uuid, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Trips : getAllTrips requested");
        if (!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return tripService.getAllTripExperience(uuid, sessionId);
    }

    // CREATE TRIP
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addTrip(@RequestBody Trip trip, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Trips : addTrip requested");
        if (!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return tripService.insertTrip(trip, sessionId);
    }
}
