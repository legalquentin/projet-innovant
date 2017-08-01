package com.etna.Controller;

import com.etna.Entity.Experience;
import com.etna.Entity.Trip;
import com.etna.Service.ConnectionService;
import com.etna.Service.ErrorService;
import com.etna.Service.ExperienceService;
import com.etna.Service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/experiences")
public class ExperienceController {

    @Autowired
    private TripService tripService;
    @Autowired
    private ExperienceService experienceService;

    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());

    // ADD ONE
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> getAllExperiences(@RequestBody Experience experience, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Experiences : AddExperience requested");
        if (!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return experienceService.AddExperience(experience, sessionId);
    }

   /* // GET ALL EXPERIENCES
    @RequestMapping(value = "/{trip_uuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllTripExperiences(@PathVariable("trip_uuid") String trip_uuid, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Experiences : getAllTripExperiences requested");
        if (!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        Trip trip = tripService.get
        return experienceService.getAllTripExperiences(trip, sessionId);
    }*/

}
