package com.etna.Service;

import com.etna.Entity.Message;
import com.etna.Entity.User;
import com.etna.Repository.MessageRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    public ResponseEntity<Object> getAllMessages(User user) {
        JSONArray mails = new JSONArray();
        for (Message item : messageRepository.findUserMails(user.getEmail())) {
            mails.put(item.recoverJsonData());
        }
        return ResponseEntity.status(HttpStatus.OK).body(mails.toString());
    }

    public ResponseEntity<Object> sendMessages(User user, Message message) {
        message.setSender(user.getEmail());
        message.setUuid(UUID.randomUUID().toString());
        message.setId(null);
        if (message.getContent() == null || message.getTitle() == null || message.getRecipient() == null) {
            return ErrorService.invalidJsonRequest("message data is invalid");
        }
        if (userService.getUserByEmail(message.getRecipient()) == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\": \"FORBIDDEN: recipient not found\"}");
        if (message.getRecipient() == user.getEmail())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"response\": \"FORBIDDEN: invalid user\"}");

        messageRepository.save(message);
        notificationService.addNotification("nouveau message","vous avez recu un nouveau message","Information", "Admin@mail.com", "New",message.getRecipient());
        return ResponseEntity.status(HttpStatus.OK).body("{\"response\": \"OK : message sent\"}");
    }
}
