package com.etna.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * Created by quentin on 21/07/2017.
 */
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Integer id;
    private String uuid;
    private String title;
    private String content;
    private String type;
    private String sender;
    private String state;
    private String recipient;

    public Notification(Integer id, String uuid, String title, String content, String type, String sender, String state, String recipient) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.type = type;
        this.sender = sender;
        this.state = state;
        this.recipient = recipient;
    }

    public Notification() {
        this.id = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String owner) {
        this.recipient = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isValid() {
        try {
            for (Field f : getClass().getDeclaredFields())
                if (f.get(this) == null)
                    return false;
        } catch (IllegalAccessException e) {
            return false;
        }
        return true;
    }
}
