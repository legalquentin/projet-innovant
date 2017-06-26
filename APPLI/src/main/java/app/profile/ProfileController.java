package app;

import org.springframework.http.HttpEntity;
import org.springframework.ui.Model;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class ProfileController {

  @Autowired
  // This means to get the bean called userRepository
  // Which is auto-generated by Spring, we will use it to handle the data
  private UserRepository userRepository;

  private static final String PATH = "/profil";

  //Profil input/output method
  @RequestMapping(value = PATH, method = RequestMethod.POST, produces = "application/json")
  public String manageProfile(@RequestHeader(value="session-id") String sessionId, @RequestParam(value = "payload", defaultValue = "{}") String payload) {
    try {
      System.out.print("sessionId = "+sessionId);

      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(payload);
      Serializer serializer = new Serializer();

      JSONObject data = (JSONObject) json.get("data");
      Long action = (Long) json.get("action");

      if (action.intValue() == 1) {
        return serializer.CreateResponseBody(200, updateProfile(data));
      }
      else {
        return getProfile((String) data.get("email"));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad Request");
    }
  }

  // public cause we want to recover the user data on the connection too, to avoid
  // doing oto many requets if we can
  public String getProfile(String email) {
    try {
      String user = userRepository.findByEmail(email).getUserJsonString();
      return "{\"status\":200, \"response\":"+user+"}";
    } catch (NullPointerException ex) {
      return "{\"status\":200, \"response\":\"{}\"}";
    }
  }

  public String getProfile(String email, String sessionId) {
    try {
      String user = userRepository.findByEmail(email).getUserJsonString();
      return "{\"status\":200, \"response\": {\"user\":"+user+", \"session-id\":\""+sessionId+"\"}}";
    } catch (NullPointerException ex) {
      return "{\"status\":200, \"response\":\"{}\"}";
    }
  }


  // think about that.... What if, and i insist on the 'IF', an authenticated user
  // change his JSONpayload in the javascript request and set the email of another guy ?
  // he then can change the data of another user.
  // Now that's when things are getting tricky, if he has a valid session token, then
  // his request well be accepted no matter what, so we must first figure if his session token
  // correspond to his email ! oohohoho i'm so great sometimes :D

  private String updateProfile(JSONObject data) {
    try {

      String email = (String) data.get("email");
      // think about the token dude !

      User user = userRepository.findByEmail(email);
      user.update(data);
      userRepository.save(user);
      return "User info Updated";

      // Exception catch for when no users are found (user = null)
    } catch (NullPointerException ex) {
      return "No user found";
    }
  }
}
