package com.etna.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;

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
    private String date;



    public Notification(String uuid, String title, String content, String type, String sender, String state, String recipient) {
        this.id = 0;
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.type = type;
        this.sender = sender;
        this.state = state;
        this.recipient = recipient;
        this.date = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
    }

    public Notification() {
        this.id = 0;
        this.date = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public JSONObject recoverJsonData() {
        try {
            JSONObject json = new JSONObject();
            json.put("uuid", this.uuid);
            json.put("title", this.title);
            json.put("content", this.content);
            json.put("type", this.type);
            json.put("sender", this.sender);
            json.put("state", this.state);
            json.put("recipient", this.recipient);
            json.put("date", this.date);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
