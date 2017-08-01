package com.etna.Service;

import com.etna.Entity.Contact;
import com.etna.Entity.User;
import com.etna.Repository.ContactRepository;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    public ResponseEntity<Object> findAllContacts(String sessionId) {

        User user = userService.getUserByEmail(ConnectionService.getSessionUser(sessionId));

        return ResponseEntity.status(HttpStatus.OK).body(getUserContacts(user).toString());
    }

    public JSONArray getUserContacts(User user) {
        JSONArray array = new JSONArray();

        for (String uuid : contactRepository.findUserFriends(user.getUuid())) {
            array.put(userService.getUserByUuid(uuid).recoverJsonData());
        }
        return array;
    }

    public ResponseEntity<Object> AddContact(Contact new_contact, String sessionId) {
        User user = userService.getUserByEmail(ConnectionService.getSessionUser(sessionId));
        /* Check if request is valid before saving the new contact */
        if (new_contact.getFriend_uuid() == null)
            return ErrorService.invalidJsonRequest("missing friend_uuid");
        User friend = userService.getUserByUuid(new_contact.getFriend_uuid());
        if (friend == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\":\"FORBIDDEN : No user found\"}");
        for (String uuid : contactRepository.findUserFriends(user.getUuid())) {
           if (uuid == friend.getUuid())
               return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\":\"FORBIDDEN : Already friend\"}");
        }
        /* Send a notification to the friend for him to accept */
        notificationService.addNotification("Demande de contact",user.getEmail()+" shouaite vous ajouter à ses contacts","Demande",user.getEmail(),"New",friend.getEmail());

        /* Save the contact */
        Contact contact = new Contact(user.getUuid(), friend.getUuid(), "Pending");
        contactRepository.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"response\":\"CREATED : Contact added\"}");
    }
}
