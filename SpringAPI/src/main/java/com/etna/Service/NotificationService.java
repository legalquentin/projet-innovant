package com.etna.Service;

import com.etna.Entity.Notification;
import com.etna.Repository.NotificationRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by quentin on 21/07/2017.
 */


@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;

    private String[] notificationsTypes = {"Information", "Annonce", "Demande"};

    private static final Logger logger = Logger.getLogger(ConnectionService.class.getName());

    // The method :getAllNotifications will return all notifications sent or
    // received by the concerned user
    public ResponseEntity<Object> getAllNotifications(String user) {
        try {
            // NO ! Don't return that unless admin or super-user
            // return ResponseEntity.status(HttpStatus.OK).body(this.notificationRepository.findAll());

            JSONObject response = new JSONObject();

            response.put("sent", getNotificationsByOwner(user));
            response.put("received", getNotificationsByOwner(user));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (JSONException e) {
            return ErrorService.standardError(e.getMessage());
        }
    }

    public ResponseEntity<Object> insertNotification(Notification notification, String user) {
        try {
            String recipient = notification.getRecipient();
            if (userService.getUserByEmail(recipient) == null)
                return ErrorService.invalidJsonRequest("InsertNotification : recipient does not exist");
            if (!Arrays.asList(notificationsTypes).contains(notification.getType()))
                return ErrorService.invalidJsonRequest("InsertNotification :type is invalid");
            notification.setSender(user);
            notification.setState("New");
            if (this.addNotification(notification) != null)
                return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"notification sent\"}");
            return ErrorService.invalidJsonRequest("null in notification");
        } catch (Exception e) {
            return ErrorService.standardError(e.getMessage());
        }
    }

    //
    public Iterable<Notification> getNotificationsByOwner(String userMail) {
        return this.notificationRepository.findByRecipient(userMail);
    }

    public Iterable<Notification> getNotificationsBySender(String userMail) {
        return this.notificationRepository.findBySender(userMail);
    }

    public Notification getNotificationsByUuid(String uuid) {
        return this.notificationRepository.findByUuid(uuid);
    }

    public String addNotification(String title, String content, String type, String sender, String state, String recipient) {
        Notification notification = new Notification(UUID.randomUUID().toString(),title,content,type,sender,state,recipient);
        if (notification.isValid()) {
            notification.setId(null);
            notificationRepository.save(notification);
            return notification.getUuid();
        }
        return null;
    }

    public String addNotification(Notification notification) {
        notification.setUuid(UUID.randomUUID().toString());
        if (notification.isValid()) {
            notification.setId(null);
            notificationRepository.save(notification);
            return notification.getUuid();
        }
        return null;
    }
}
