package com.etna.Service;

import com.etna.Dao.OfferDao;
import com.etna.Entity.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by quentin on 29/06/2017.
 */

@Service
public class OfferService {

    @Autowired
    private OfferDao offerDao;

    public Collection<Offer> getAllOffers() {
        return this.offerDao.getAllOffers();
    }

    public Offer getOfferById(Integer id) {
        return this.offerDao.getOfferById(id);
    }

    public void removeOfferById(int id) {
        this.offerDao.removeOfferById(id);
    }

    public void updateOffer(Offer offer) {
        this.offerDao.updateOffer(offer);
    }

    public void insertOffer(Offer offer) {
        this.offerDao.insertOffer(offer);
    }

}
