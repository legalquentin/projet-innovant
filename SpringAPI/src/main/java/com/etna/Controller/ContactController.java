package com.etna.Controller;

import com.etna.Entity.Contact;
import com.etna.Service.ConnectionService;
import com.etna.Service.ContactService;
import com.etna.Service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private ContactService contactService;

    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllContacts(@RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Notifications : getAllNotifications requested");
        if(!connectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return contactService.findAllContacts(sessionId);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> AddContact(@RequestBody Contact contact, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Notifications : getAllNotifications requested");
        if(!connectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return contactService.AddContact(contact, sessionId);
    }

}
