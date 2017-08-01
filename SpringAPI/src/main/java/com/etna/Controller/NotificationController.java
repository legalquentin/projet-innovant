package com.etna.Controller;

import com.etna.Entity.Notification;
import com.etna.Service.ConnectionService;
import com.etna.Service.ErrorService;
import com.etna.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;


@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ConnectionService connectionService;

    private static final Logger LOGGER = Logger.getLogger(OfferController.class.getName());

    // GET ALL NOTIFICATIONS (for the logged user)
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllNotifications(@RequestHeader(value="session-id") String sessionId) {
        LOGGER.info("/Notifications : getAllNotifications requested");
        if(!connectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return notificationService.getAllNotifications(ConnectionService.getSessionUser(sessionId));
    }

    // ADD A NOTIFICATION
   /* @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> addNotification(@RequestBody Notification notification, @RequestHeader("session-id") String sessionId) {
        LOGGER.info("/Notifications : getAllNotifications requested");
        if(!connectionService.checkToken(sessionId)) {
            return ErrorService.unauthenticatedJson();
        }
        return notificationService.insertNotification(notification, ConnectionService.getSessionUser(sessionId));
    }*/
}
