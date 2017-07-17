package com.etna.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by quentin on 29/06/2017.
 */
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Integer id;
    private String uuid;
    private String title;
    private String content;
    private String author;

    public Offer(int id, String uuid, String title, String content, String author) {
         this.id = id;
         this.uuid = uuid;
         this.title = title;
         this.content = content;
         this.author = author;
    }

    public Offer() {}

    public Offer(Offer offer) {
        this.id = offer.getId();
        this.uuid = offer.getUuid();
        this.title = offer.getTitle();
        this.content = offer.getContent();
        this.author = offer.getAuthor();
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
