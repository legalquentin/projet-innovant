package app.connection;

import app.profile.ProfileController;
import app.user.*;
import app.user.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
<<<<<<< HEAD
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
=======
>>>>>>> 68a3baf0f893863c5a6bdfcf80d24f7a9c79f624
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class ConnectionController {

  private static final String FORM_VIEW = "/register";
  private static final String WELCOME_VIEW = "/home";//"newuser.page";
  private static final String PATH = "/connection";
  private static Serializer serializer = new Serializer();

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProfileController profileController;

  @RequestMapping(value = PATH, method = RequestMethod.GET)
  public String connect() {
    System.out.println("connection as 'get' is invalid !");
    return "connection form invalid";
  }

  @ModelAttribute("User")
  public User populateUser() {
    User user = new User();
    return user;
  }

  //REGISTER
  @CrossOrigin(origins = "*")
  @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
  public String register(@RequestBody JSONObject json) {
    try {
      String email = (String) json.get("email");
      for (User item : getAllUsers()) {
        if (item.getEmail().equals(email))
          return serializer.CreateResponseBody(403, "email is already registered"); // make token
      }
      User user = new User();
      user.setEmail(email);
      user.setPassword(serializer.encodePwd((String) json.get("password")));
      userRepository.save(user);
      return profileController.getProfile(email, serializer.bakeToken(email));
    } catch (Exception ex) {
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad Request");
    }
  }

  // LOGIN
  @CrossOrigin(origins = "*")
  @RequestMapping(value = "/connection", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
  public String manageProfile(@RequestBody JSONObject json) {
    try {
      for (User item : getAllUsers()) {
        String pwd = serializer.encodePwd((String) json.get("password"));
        String email = (String) json.get("email");
        if (email.equals(item.getEmail()) && pwd.equals(item.getPassword())) {
          return profileController.getProfile(email, serializer.bakeToken(email));
        }
      }
      return serializer.CreateResponseBody(403, "Unknown login or password");
    } catch (Exception ex) {
      ex.printStackTrace();
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad Request");
    }
  }

  // LOGOUT
  @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
  public String logout(@RequestParam(value = "payload", defaultValue = "{}") String payload, @RequestHeader(value="session-id") String sessionId) {
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(payload);
      for (User item : getAllUsers()) {
        String email = (String) json.get("email");
        if (email.equals(item.getEmail()) && serializer.checkToken(email, sessionId)) {
          return "200";
        }
      }
      return "403";
    } catch (Exception ex) {
      ex.printStackTrace();
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad Request");
    }
  }

  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }
}
