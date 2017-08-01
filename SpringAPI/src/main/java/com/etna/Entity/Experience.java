package com.etna.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DateFormat;
import java.util.Date;

@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Integer id;
    private Integer number;
    private String uuid;
    private String trip_uuid;
    private String offer_uuid;
    private String owner;
    private String title;
    private String content;
    private String creation_date;
    private String date_start;
    private String date_end;

    public Experience() {
        this.creation_date = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
    }

    public Experience(String uuid, String trip_uuid, String owner, Integer number, String title, String content) {
        this.uuid = uuid;
        this.trip_uuid = trip_uuid;
        this.owner = owner;
        this.number = number;
        this.title = title;
        this.content = content;
        this.creation_date = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
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

    public String getTrip_uuid() {
        return trip_uuid;
    }

    public void setTrip_uuid(String trip_uuid) {
        this.trip_uuid = trip_uuid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getOffer_uuid() {
        return offer_uuid;
    }

    public void setOffer_uuid(String offer_uuid) {
        this.offer_uuid = offer_uuid;
    }

    public JSONObject recoverJsonData() {
        try {
            JSONObject json = new JSONObject();
            json.put("uuid", this.uuid);
            json.put("owner", this.owner);
            json.put("trip_uuid", this.trip_uuid);
            json.put("number", this.number);
            json.put("title", this.title);
            json.put("offer_uuid", this.offer_uuid);
            json.put("content", this.content);
            json.put("creation_date", this.creation_date);
            json.put("date_start", this.date_start);
            json.put("date_end", this.date_end);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
