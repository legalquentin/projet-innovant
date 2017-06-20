package app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.soap.SAAJResult;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Integer id;
    private Integer dateBirth;
    private Integer dateJoin;
    private Integer xp;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String picture;
    private String location;
    private boolean newsletter;

/*    @OneToOne
    @JoinColumn(name = "travelerProfile")
    TravelerProfile travelerProfile;*/

    public User() {}

    public void setName(String name) {
      this.name = name;
    }
    public void setEmail(String email) {
      this.email = email;
    }
    public void setPassword(String password) {
      this.password = password;
    }
    public void setId(Integer id) {
		   this.id = id;
	}
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setDateBirth(Integer dateBirth) {
        this.dateBirth = dateBirth;
    }
    public void setDateJoin(Integer dateJoin) {
        this.dateJoin = dateJoin;
    }
    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }
    public void setXp(Integer xp) {
        this.xp = xp;
    }


    public Integer getId() {
	     return id;
	  }
    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
    public String getPicture() {
        return picture;
    }
    public String getEmail() {
        return email;
    }
    public String getLocation() {
        return location;
    }
    public String getPassword() {
        return password;
    }
    public Integer getDateBirth() {
        return dateBirth;
    }
    public Integer getDateJoin() {
        return dateJoin;
    }
    public boolean getNewsletter() {
        return newsletter;
    }
    public Integer getXp() {
        return xp;
    }
}