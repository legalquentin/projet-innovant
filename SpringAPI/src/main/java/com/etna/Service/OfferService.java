package com.etna.Service;

//import com.etna.Dao.OfferDao;
import com.etna.Entity.Notification;
import com.etna.Entity.Offer;
import com.etna.Entity.User;
import com.etna.Repository.OfferRepository;
import com.etna.Repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by quentin on 29/06/2017.
 */

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(OfferService.class.getName());

    public ResponseEntity<Object> getAllOffers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.offerRepository.findAll());
    }

    public ResponseEntity<Object> getOfferById(String uuid) {
        Offer ofr = new Offer();
        ofr = this.offerRepository.findByUuid(uuid);
        if(ofr == null)
            return ResponseEntity.status(HttpStatus.OK).body("{}");
        return ResponseEntity.status(HttpStatus.OK).body(ofr);
    }

    // maybe think about doing a backup of the offer somewhere ?
    public ResponseEntity<Object> removeOfferById(String uuid, String email) {
        JSONObject obj = new JSONObject();
        try {
            Offer offer = this.offerRepository.findByUuid(uuid);
            if (offer == null) {
                obj.put("response", "NOT_FOUND : the offer does not exist");
                logger.warning("OFFER = NULL");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obj.toString());
            }
            else if (offer.getAuthor().equals(email)) {
                this.offerRepository.delete(offer);
                logger.info("Deleted offer : " + offer.getTitle() + " by : " + offer.getAuthor() + " at : " + Instant.now());
                obj.put("response", "Deleted offer : " + offer.getTitle() + " by : " + offer.getAuthor());
                return ResponseEntity.status(HttpStatus.OK).body(obj);
            } else {
                obj.put("response", "FORBIDDEN : Not the offer Author");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(obj.toString());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> updateOffer(Offer offer, String user) {
        try {
            JSONObject obj = new JSONObject();
            Offer ofr = this.offerRepository.findByUuid(offer.getUuid());
            if (offer.getAuthor() != user) {
                return ErrorService.unauthenticatedJson();
            }
            ofr.setTitle(offer.getTitle());
            ofr.setContent(offer.getContent());
            this.offerRepository.save(ofr);
            obj.put("response", "OK : Offer "+offer.getTitle()+" of "+offer.getAuthor()+" modifications has been saved");
            return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
        } catch (Exception ex) {
            return ErrorService.standardError(ex.getMessage());
        }
    }

    public ResponseEntity<Object> insertOffer(Offer offer) {
        try {
            JSONObject obj = new JSONObject();
            offer.setId(null);
            offer.setUuid(UUID.randomUUID().toString());
            if(offer.getTitle() == null || offer.getContent() == null) {
                logger.info("Fail to insert offer from : " + offer.getAuthor());
                obj.put("response", "FORBIDDEN : Fail to insert offer, Content and title cannot be null");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(obj.toString());
            } else {
                offer.setState("open");
                offerRepository.save(offer);
                logger.info("Inserted offer : " + offer.getUuid());
                obj.put("response", offer.getUuid());
                return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
            }
        } catch (Exception ex) {
            return ErrorService.standardError(ex.getMessage());
        }
    }

    public ResponseEntity<Object> applyOffer(JSONObject application) {
        try {
            String user = application.getString("applicant");
            String offerUuid = application.getString("uuid");

            User applicant = userService.getUserByEmail(user);
            Offer offer = offerRepository.findByUuid(offerUuid);
            // offerUuid is invalid
            if (offer == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\": \"FORBIDDEN: uuid is invalid\"}");
            if (offer.getApplicants().contains(applicant.getUuid()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\": \"FORBIDDEN: already registered\"}");

            offer.addApplicants(applicant.getUuid());
            offerRepository.save(offer);
            Notification notification = new Notification(0,null,"Réponse à l'offre","Bonjour je veut postuler","",user,"Unread",offer.getAuthor());
            if (notificationService.addNotification(notification))
                return ResponseEntity.status(HttpStatus.OK).body("{\"response\": \"OK: application submited\"}");
        } catch (JSONException e) {
            return ErrorService.invalidJsonRequest(e.getMessage());
        } catch (Exception e) {
            return ErrorService.standardError(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("mdr");
    }
}
