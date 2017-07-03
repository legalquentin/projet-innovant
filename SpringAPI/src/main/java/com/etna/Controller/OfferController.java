package com.etna.Controller;

import com.etna.Entity.Offer;
import com.etna.Service.ConnectionService;
import com.etna.Service.OfferService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

/**
 * Created by quentin on 29/06/2017.
 */

@RestController
@RequestMapping("/Offers")
public class OfferController {

    @Autowired
    private OfferService offerService ;
    @Autowired
    private ConnectionService connectionService;
    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllOffers(@RequestHeader(value="session-id") String sessionId) {
        LOGGER.info("/Offers : getAllOffers requested");
        if(!connectionService.checkToken(sessionId)) {
            return connectionService.unauthenticatedJson();
        }
        return offerService.getAllOffers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getOfferById(@PathVariable("id") int id, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : getOfferById requested");
        if(!connectionService.checkToken(sessionId)) {
            return connectionService.unauthenticatedJson();
        }
        return offerService.getOfferById(id);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Object> deleteOfferById(@PathVariable("uuid") String uuid, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : deleteOfferById requested");
        if(!connectionService.checkToken(sessionId)) {
            return connectionService.unauthenticatedJson();
        }
        return offerService.removeOfferById(uuid, connectionService.getSessionUser(sessionId));
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> updateOffer(@RequestBody Offer offer, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : updateOffer requested");
        if(!connectionService.checkToken(sessionId, offer.getAuthor())) {
            return connectionService.unauthenticatedJson();
        }
        return offerService.updateOffer(offer);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> insertOffer(@RequestBody Offer offer, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Offers : insertOffer requested");
        try {
            if (!connectionService.checkToken(sessionId, offer.getAuthor())) {
                if (!connectionService.checkToken(sessionId)) {
                    return connectionService.unauthenticatedJson();
                }
                LOGGER.warning("/Offers : " + connectionService.getSessionUser(sessionId) + " tried to usurp : " + offer.getAuthor());
                offer.setAuthor(connectionService.getSessionUser(sessionId));
            }
            return offerService.insertOffer(offer);
        } catch (Exception ex) {
            ex.printStackTrace();
            //LOGGER.warning(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"response\":" +
                    " \"INTERNAL_SERVER_ERROR: something went wrong, oups\"}");
        }
    }
}
