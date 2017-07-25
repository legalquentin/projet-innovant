package com.etna.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

/*
 * Created by quentin on 29/06/2017.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Integer id;
    private Integer xp;
    private boolean newsletter;
    private String dateBirth;
    private String dateJoin;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String password;
    private String picture;
    private String uuid;
    private Vector<String> contacts;

    public User() {
        dateJoin = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format(new Date());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vector<String> getContacts() {
        return contacts;
    }

    public void setContacts(Vector<String> contacts) {
        this.contacts = contacts;
    }

    public void addContact(String uuid) {
        this.contacts.add(uuid);
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(String dateJoin) {
        this.dateJoin = dateJoin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public JSONObject recoverJsonData() {
        try {
            JSONObject json = new JSONObject();
            json.put("uuid", this.uuid);
            json.put("xp", this.xp);
            json.put("newsletter", this.newsletter);
            json.put("dateBirth", this.dateBirth);
            json.put("dateJoin", this.dateJoin);
            json.put("firstName", this.firstName);
            json.put("lastName", this.lastName);
            json.put("email", this.email);
            json.put("country", this.country);
            json.put("picture", this.picture);
            json.put("contacts", this.contacts);
            json.put("dateJoin", this.dateJoin);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }


}
