package com.etna.Controller;

import com.etna.Entity.Message;
import com.etna.Service.ConnectionService;
import com.etna.Service.ErrorService;
import com.etna.Service.MessageService;
import com.etna.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    private static final Logger LOGGER = Logger.getLogger(com.etna.Controller.OfferController.class.getName());


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllMessages(@RequestHeader(value="session-id") String sessionId) {
        LOGGER.info("/Messages : getAllMessages requested");
        if(!connectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return messageService.getAllMessages(userService.getUserByEmail(ConnectionService.getSessionUser(sessionId)));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> sendMessage(@RequestBody Message message, @RequestHeader(value="session-id") String sessionId) {
        LOGGER.info("/Messages : getAllMessages requested");
        if(!connectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return messageService.sendMessages(userService.getUserByEmail(ConnectionService.getSessionUser(sessionId)), message);
    }
}
