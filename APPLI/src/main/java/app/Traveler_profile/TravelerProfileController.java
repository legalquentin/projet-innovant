package app.Traveler_profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TravelerProfileController {

    @Autowired
    private TravelerProfileRepository travelerProfileRepository;


    @RequestMapping(value = "/travellerProfile", method = RequestMethod.POST, produces = "application/json")
    public String connect(@ModelAttribute("TravelerPtofile") TravelerProfile travelerProfile,
                          BindingResult result, Model model)
    {
        travelerProfileRepository.save(travelerProfile);
        return "200";
    }

    /*public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }*/

}