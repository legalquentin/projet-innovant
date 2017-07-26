package com.etna.Service;

//import com.etna.Dao.OfferDao;
import com.etna.Entity.Application;
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
    @Autowired
    private ApplicationService applicationService;

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
                logger.warning("/DeleteOffer failed, reason : not_found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obj.toString());
            }
            else if (offer.getAuthor().equals(email)) {
                this.offerRepository.delete(offer);
                logger.info("Deleted offer : " + offer.getTitle() + " by : " + offer.getAuthor() + " at : " + Instant.now());
                obj.put("response", "Deleted offer : " + offer.getTitle() + " by : " + offer.getAuthor());
                return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
            } else {
                obj.put("response", "FORBIDDEN : Not the offer Author");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(obj.toString());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> updateOffer(Offer offer, String session_id) {
        try {
            if (offer.getUuid() == null || offer.getTitle() == null || offer.getContent() == null || offer.getState() == null)
                return ErrorService.invalidJsonRequest("Invalid Offer Format");
            Offer ofr = offerRepository.findByUuid(offer.getUuid());
            if (ofr == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"response\":\"No offer found\"}");
            logger.info("ofr.getAuthor() = '"+ofr.getAuthor()+"' and connection user = '"+ConnectionService.getSessionUser(session_id)+"'");
            if (!ConnectionService.checkToken(session_id, ofr.getAuthor()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\":\"Not the offer owner\"}");
            ofr.setTitle(offer.getTitle());
            ofr.setContent(offer.getContent());
            ofr.setState(offer.getState());
            offerRepository.save(ofr);
            notificationService.addNotification("Modification", "Vous avez modifié une annonce", "Information", "Admin@mail.com", "New", offer.getAuthor());
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"Modifications saved\"}");
        } catch (Exception ex) {
            ex.printStackTrace();
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
                notificationService.addNotification("Annonce", "Vous avez posté une annonce", "Annonce", "Admin@mail.com", "New", offer.getAuthor());
                logger.info("Inserted offer : " + offer.getUuid());
                obj.put("response", offer.getUuid());
                return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
            }
        } catch (Exception ex) {
            return ErrorService.standardError(ex.getMessage());
        }
    }

    public ResponseEntity<Object> applyOffer(Application application, User applicant) {
        try {
            String user_uuid = applicant.getUuid();
            String offer_uuid = application.getOffer_uuid();

            Offer offer = offerRepository.findByUuid(offer_uuid);
            // offerUuid is invalid

            if (offer == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\": \"FORBIDDEN: no offer found\"}");
            }
            logger.warning("user_uuid : "+user_uuid+" and offer_uuid :"+offer_uuid);
            Application app = applicationService.findUserInOfferApplication(offer_uuid, user_uuid);
//            logger.warning("app : "+app.getState());
            if ( app != null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\": \"FORBIDDEN: already applied\"}");
            }
            /* OK then proceed to send a notification and save the application */

            String uuid = notificationService.addNotification("Demande", "Bonjour, j'aimerais répondre à votre demande", "Demande", applicant.getEmail(), "New", offer.getAuthor());

//            Notification notification = new Notification(null,"Réponse à l'offre","Bonjour je veut postuler","",applicant.getEmail(),"Unread",offer.getAuthor());
            if (uuid != null) {
                applicationService.addApplicant(offer_uuid, user_uuid, "New",uuid);
                return ResponseEntity.status(HttpStatus.OK).body("{\"response\": \"OK: application submited\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorService.invalidJsonRequest(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("mdr");
    }
}
