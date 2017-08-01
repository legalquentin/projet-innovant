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
public class Trip {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Integer id;
    private String name;
    private String uuid;
    private String owner;
    private String owner_uuid;
    private String cover;
    private String creation_date;
    private String date_start;
    private String date_end;

    public Trip() {
    }

    public Trip(String name, String uuid, String owner, String owner_uuid, String cover) {
        this.name = name;
        this.uuid = uuid;
        this.owner = owner;
        this.owner_uuid = owner_uuid;
        this.cover = cover;
        this.creation_date = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner_uuid() {
        return owner_uuid;
    }

    public void setOwner_uuid(String owner_uuid) {
        this.owner_uuid = owner_uuid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public JSONObject recoverJsonData() {
        try {
            JSONObject json = new JSONObject();
            json.put("uuid", this.uuid);
            json.put("owner", this.owner);
            json.put("owner_uuid", this.owner_uuid);
            json.put("cover", this.cover);
            json.put("name", this.name);
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
