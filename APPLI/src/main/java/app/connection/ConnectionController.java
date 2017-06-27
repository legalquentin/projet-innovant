package app;

// import app.profile.ProfileController;
// import app.user.*;
// import app.user.UserRepository;
import java.util.logging.Logger;
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
public class ConnectionController {

  private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
  private static Serializer serializer = new Serializer();

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProfileController profileController;

  @RequestMapping(value = "/connection", method = RequestMethod.GET)
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
      LOGGER.info("/Register requested from : "+(String) json.get("email"));
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
      LOGGER.warning("Exception on /register -- Cause: "+ ex.getMessage());
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad Request");
    }
  }

  // LOGIN
  @CrossOrigin(origins = "*")
  @RequestMapping(value = "/connection", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
  public String manageProfile(@RequestBody JSONObject json) {
    try {
     LOGGER.info("/Connection requested from : "+(String) json.get("email"));
      for (User item : getAllUsers()) {
        String pwd = serializer.encodePwd((String) json.get("password"));
        String email = (String) json.get("email");
        if (email.equals(item.getEmail()) && pwd.equals(item.getPassword())) {
          return profileController.getProfile(email, serializer.bakeToken(email));
        }
      }
      return serializer.CreateResponseBody(403, "Unknown login or password");
    } catch (Exception ex) {
      LOGGER.warning("Exception on /connection -- Cause: "+ ex.getMessage());
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad Request");
    }
  }

  // LOGOUT
  @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
  public String logout(@RequestBody JSONObject json, @RequestHeader(value="session-id") String sessionId) {
    try {
      LOGGER.info("/Logout requested from : "+(String) json.get("email"));
      String email = (String) json.get("email");
      if (email.equals("tessts") && serializer.checkToken(email, sessionId)) {
        return serializer.CreateResponseBody(200, "Successfully Disconected");
      }
      return serializer.CreateResponseBody(403, "Unauthenticated request");
    } catch (Exception ex) {
      LOGGER.warning("Exception on /logout -- Cause: "+ ex.getMessage());
      Serializer serializer = new Serializer();
      return serializer.CreateResponseBody(400, "Bad Request");
    }
  }

  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }
}
