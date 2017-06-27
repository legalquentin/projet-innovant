package app;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import java.util.UUID;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;


@RestController
public class OfferController {

  private static Serializer serializer = new Serializer();
  private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());


  @Autowired
  private OfferRepository offerRepository;

  @ModelAttribute("Offer")
  public Offer populateOffer() {
    Offer offer = new Offer();
    return offer;
  }

  // ListOffer
  @CrossOrigin(origins = "*")
  @RequestMapping(value = "/getOffers", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
  public String ListOffer(@RequestBody JSONObject json, @RequestHeader(value="session-id") String sessionId) {
    try {
      LOGGER.info("/GetOffers requested from : "+(String) json.get("email"));
      if (!serializer.checkToken((String) json.get("email"), sessionId))
        return serializer.CreateResponseBody(403, "Unauthenticated request");

      JSONArray offerList = new JSONArray();
      for (Offer offer : getAllOffers()) {
        offerList.add(offer.getOfferJson());
      }
      // JSONObject response = new JSONObject();
      // response.put("Offers", offerList);
      return serializer.CreateResponseBody(200, offerList);

    } catch (Exception ex) {
      LOGGER.warning("Exception on /getOffers -- Cause: "+ ex.getMessage());
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad request");
    }
  }

  // AddOffer
  @CrossOrigin(origins = "*")
  @RequestMapping(value = "/addOffer", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
  public String addOffer(@RequestBody JSONObject json, @RequestHeader(value="session-id") String sessionId) {
    try {
      String email = (String) json.get("email");
      LOGGER.info("/AddOffer requested from : "+(String) json.get("email"));
      if (!serializer.checkToken(email, sessionId))
        return serializer.CreateResponseBody(403, "Unauthenticated request");

      // create the offer object
      Offer offer = new Offer();
      offer.update(json);
      offer.setUuid(UUID.randomUUID().toString());

      // save offer to db
      offerRepository.save(offer);

      return serializer.CreateResponseBody(200, "New offer saved");

    } catch (Exception ex) {
      LOGGER.warning("Exception on /addOffer -- Cause: "+ ex.getMessage());
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad request");
    }
  }

  public @ResponseBody Iterable<Offer> getAllOffers() {
    return offerRepository.findAll();
  }
}
