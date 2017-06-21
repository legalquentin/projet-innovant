package app;

import org.springframework.http.HttpEntity;
import org.springframework.ui.Model;
import java.util.Arrays;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class TravelerProfileController {


    @RequestMapping(value = "/travellerProfile", method = RequestMethod.GET, produces = "application/json")
    public String connect(@ModelAttribute("User") User user,
                          BindingResult result, Model model)
    {
        return "hello";
    }

    /*public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }*/

}