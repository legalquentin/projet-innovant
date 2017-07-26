package com.etna.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Integer id;
    private String user_uuid;
    private String friend_uuid;
    private String state;

    public Contact(String user_uuid, String friend_uuid, String state) {
        this.user_uuid = user_uuid;
        this.friend_uuid = friend_uuid;
        this.state = state;
    }

    public Contact() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getFriend_uuid() {
        return friend_uuid;
    }

    public void setFriend_uuid(String friend_uuid) {
        this.friend_uuid = friend_uuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
