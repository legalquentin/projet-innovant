package com.etna.Controller;


import com.etna.Entity.User;
import com.etna.Service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
 * Created by quentin on 30/06/2017.
 * This 'ConnectionController' handle incoming request to login/register and the authenticate user with a token
 * It also will be the handler of logout and all global connection properties;
 */

@RestController
@RequestMapping("/connect")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService ;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        return connectionService.registerUser(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        return connectionService.authenticateUser(user);
    }

    @RequestMapping("/listTokens")
    public Map<String,String> ListTokens() {
        return connectionService.getSessionsTokens();
    }
}
