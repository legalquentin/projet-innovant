package com.etna.Controller;

import com.etna.Entity.Application;
import com.etna.Entity.Offer;
import com.etna.Entity.User;
import com.etna.Service.ConnectionService;
import com.etna.Service.ErrorService;
import com.etna.Service.OfferService;
import com.etna.Service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by quentin on 29/06/2017.
 */

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService ;
    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());


    // GET ALL OFFERS
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllOffers(@RequestHeader(value="session-id") String sessionId) {
        LOGGER.info("/Offers : getAllOffers requested");
        if(!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return offerService.getAllOffers();
    }

    // GET ONE OFFER
    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getOfferById(@PathVariable("uuid") String uuid, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : getOfferById requested");
        if(!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return offerService.getOfferById(uuid);
    }

    // DELETE ONE OFFER
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Object> deleteOfferById(@PathVariable("uuid") String uuid, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : deleteOfferById requested");
        if(!ConnectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return offerService.removeOfferById(uuid, ConnectionService.getSessionUser(sessionId));
    }

    // UPDATE OFFER
    @RequestMapping(method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> updateOffer(@RequestBody Offer offer, @RequestHeader("session-id") String sessionId) {
        try {
            LOGGER.info("/Offers : updateOffer requested");
            if (!ConnectionService.checkToken(sessionId)) {
                return ErrorService.unauthenticatedJson();
            }
            return offerService.updateOffer(offer, sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorService.standardError("damn");
        }
    }

    // APPLY TO OFFER
    @RequestMapping(path = "/apply", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> applyOffer(@RequestBody Application application, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : applyOffer requested");
        try {
            LOGGER.info("date : "+new Date());
            if (!ConnectionService.checkToken(sessionId))
                return ErrorService.unauthenticatedJson();
            User user = userService.getUserByEmail(ConnectionService.getSessionUser(sessionId));
            application.setUser_uuid(user.getUuid());
            return offerService.applyOffer(application, user);
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorService.standardError(e.getMessage());
        }
    }

    // CREATE OFFER
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> insertOffer(@RequestBody Offer offer, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : insertOffer requested");
        if (!ConnectionService.checkToken(sessionId, offer.getAuthor())) {
            if (!ConnectionService.checkToken(sessionId)) {
                return ErrorService.unauthenticatedJson();
            }
            LOGGER.warning("/Offers : " + ConnectionService.getSessionUser(sessionId) + " tried to usurp : " + offer.getAuthor());
            offer.setAuthor(ConnectionService.getSessionUser(sessionId));
        }
        return offerService.insertOffer(offer);
    }
}
