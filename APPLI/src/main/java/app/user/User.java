package app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.xml.soap.SAAJResult;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Integer id;
    private String dateBirth;
    private String dateJoin;
    private Integer xp;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String picture;
    private String location;
    private boolean newsletter;

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
    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }
    public void setDateJoin(String dateJoin) {
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
    public String getDateBirth() {
        return dateBirth;
    }
    public String getDateJoin() {
        return dateJoin;
    }
    public boolean getNewsletter() {
        return newsletter;
    }
    public Integer getXp() {
        return xp;
    }

    public String getUserJsonString() {
      JSONObject jsonObject = new JSONObject();

      Map<String, String> map = new HashMap<String, String>();
      map.put("id", String.valueOf(id));
      map.put("dateBirth", String.valueOf(dateBirth));
      map.put("dateJoin", String.valueOf(dateJoin));
      map.put("xp", String.valueOf(xp));
      map.put("name", String.valueOf(name));
      map.put("surname", String.valueOf(surname));
      map.put("email", String.valueOf(email));
      map.put("password", String.valueOf(password));
      map.put("picture", String.valueOf(picture));
      map.put("location", String.valueOf(location));
      map.put("newsletter", (newsletter? "true" : "false"));
      jsonObject.put("User",map);
      return jsonObject.toJSONString();
    }

    public void update(JSONObject data) {
      this.dateBirth = (String) data.get("dateBirth");
      this.dateJoin = (String) data.get("dateJoin");
      Long tmplong = (Long) data.get("xp");
      this.xp = tmplong.intValue();
      this.name = (String) data.get("name");
      this.surname = (String) data.get("surname");
      this.password = (String) data.get("password");
      this.picture = (String) data.get("picture");
      this.location = (String) data.get("location");
      this.newsletter = Boolean.valueOf((String)data.get("newsletter"));
    }
}
