package app;

import org.json.simple.JSONObject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;


@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Integer id;
    private String uuid;
    private String title;
    private String content;
    private String author;

/*    @OneToOne
    @JoinColumn(name = "travelerProfile")
    TravelerProfile travelerProfile;*/

    public Offer() {}

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public JSONObject getOfferJson() {
      JSONObject obj = new JSONObject();
      obj.put("uuid", String.valueOf(uuid));
      obj.put("title", String.valueOf(title));
      obj.put("content", String.valueOf(content));
      return obj;
    }

    public void update(JSONObject data) {
      this.uuid = (String) data.get("uuid");
      this.title = (String) data.get("title");
      this.content = (String) data.get("content");
      this.author = (String) data.get("email");
    }
}
