package com.etna.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Integer id;
    private String uuid;
    private String sender;
    private String recipient;
    private String title;
    private String content;

    public Message() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Message(String sender, String recipient, String title, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.title = title;
        this.content = content;
        this.uuid = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public JSONObject recoverJsonData() {
        try {
            JSONObject json = new JSONObject();
            json.put("uuid", this.uuid);
            json.put("sender", this.sender);
            json.put("recipient", this.recipient);
            json.put("title", this.title);
            json.put("content", this.content);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
