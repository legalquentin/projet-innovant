package com.etna.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DateFormat;
import java.util.Date;

/* This entity represent an Application to an offer */
/* It contain the user_uuid (who have applied), the offer_uuid, the state and the notification_uuid*/

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Integer id;
    private String user_uuid;
    private String offer_uuid;
    private String state;
    private String notification_uuid;
    private String application_date;

    public Application() {
        this.application_date = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
    }

    public Application(String user_uuid, String offer_uuid, String state, String notification_uuid) {
        this.user_uuid = user_uuid;
        this.offer_uuid = offer_uuid;
        this.state = state;
        this.notification_uuid = notification_uuid;
        this.application_date = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
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

    public String getOffer_uuid() {
        return offer_uuid;
    }

    public void setOffer_uuid(String offer_uuid) {
        this.offer_uuid = offer_uuid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNotification_uuid() {
        return notification_uuid;
    }

    public void setNotification_uuid(String notification_uuid) {
        this.notification_uuid = notification_uuid;
    }

    public String getApplication_date() {
        return application_date;
    }

    public void setApplication_date(String application_date) {
        this.application_date = application_date;
    }
}
