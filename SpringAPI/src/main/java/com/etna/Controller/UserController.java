package com.etna.Controller;

import com.etna.Entity.User;
import com.etna.Service.ConnectionService;
import com.etna.Service.ErrorService;
import com.etna.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

/**
 * Created by quentin on 29/06/2017.
 */

@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ConnectionService connectionService;
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    // GET ALL
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllUsers(@RequestHeader("session-id") String sessionId) {
        try {
            LOGGER.info("/User : getAllUsers requested");
            if (!connectionService.checkToken(sessionId)) {
                return ErrorService.unauthenticatedJson();
            }
            return userService.getAllUsers();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }

    // GET ONE
    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    // DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") int id) {
        userService.removeUserById(id);
    }

    // UPDATE ONE
    @RequestMapping(method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public void insertUser(@RequestBody User user) {
//        userService.insertUser(user);
//    }

}
