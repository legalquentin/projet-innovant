package com.etna.Service;

//import com.etna.Dao.OfferDao;
import com.etna.Entity.Offer;
import com.etna.Repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by quentin on 29/06/2017.
 */

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Iterable<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    public Offer getOfferById(Integer id) {
        return this.offerRepository.findOne(id);
    }

    public void removeOfferById(int id) {
        this.offerRepository.delete(id);
    }

    public void updateOffer(Offer offer) {
        Offer ofr = this.offerRepository.findOne(offer.getId());
        ofr.setTitle(offer.getTitle());
        ofr.setContent(offer.getContent());
        this.offerRepository.save(ofr);
    }

    public void insertOffer(Offer offer) {
        this.offerRepository.save(offer);
    }

}
