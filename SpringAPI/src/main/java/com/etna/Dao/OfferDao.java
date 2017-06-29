package com.etna.Dao;

import com.etna.Entity.Offer;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by quentin on 29/06/2017.
 */

@Repository
public class OfferDao {

    private static Map<Integer, Offer> offers;

    static {

        offers = new HashMap<Integer, Offer>() {

            {
                put(1, new Offer(1,"12345678","Test","content","quentin@mail.com"));
                put(2, new Offer(2, "98076543","OtherTest", "a small content", "quentin@mail.com"));
            }

        };
    }
    public Collection<Offer> getAllOffers() {
        return this.offers.values();
    }

    public Offer getOfferById(Integer id) {
        return this.offers.get(id);
    }

    public void removeOfferById(int id) {
        this.offers.remove(id);
    }

    public void updateOffer(Offer offer) {
        Offer ofr = this.offers.get(offer.getId());
        //ofr.setId(offer.getId());
        ofr.setTitle(offer.getTitle());
        ofr.setAuthor(offer.getAuthor());
        ofr.setContent(offer.getContent());
    }

    public void insertOffer(Offer offer) {
        this.offers.put(offer.getId(), offer);
    }
}
