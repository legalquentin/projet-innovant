package app.connection;

import app.profile.ProfileController;
import app.user.*;
import app.user.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
  @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
  public String register(@ModelAttribute("User") User user,
  BindingResult result, Model model) {
    for (User item : getAllUsers()) {
      if (item.getEmail().equals(user.getEmail()))
        return "403"; // make token
    }
    user.setPassword(serializer.encodePwd(user.getPassword()));
    userRepository.save(user);
    return "200";
  }

  @RequestMapping(value = "/connection", method = RequestMethod.POST, produces = "application/json")
  public String manageProfile(@RequestParam(value = "payload", defaultValue = "{}") String payload) {
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(payload);
      for (User item : getAllUsers()) {
        String pwd = serializer.encodePwd((String) json.get("password"));
        String email = (String) json.get("email");
        if (email.equals(item.getEmail()) && pwd.equals(item.getPassword())) {
          return profileController.getProfile(email, "");
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
