package com.etna.Controller;

import com.etna.Entity.Offer;
import com.etna.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());


    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Offer> getAllOffers(@RequestHeader(value="session-id") String sessionId) {
        LOGGER.info("/Offers requested");
        return offerService.getAllOffers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Offer getOfferById(@PathVariable("id") int id) {
        return offerService.getOfferById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOfferById(@PathVariable("id") int id) {
        offerService.removeOfferById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateOffer(@RequestBody Offer offer) {
        offerService.updateOffer(offer);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void insertOffer(@RequestBody Offer offer) {
        offerService.insertOffer(offer);
    }
}
