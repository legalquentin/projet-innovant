package app;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private static final String template = "This is the, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Map<String, String> sessionsTokens = new HashMap<String, String>();

    @RequestMapping("/api")
    public Api api(@RequestParam(value="name", defaultValue="API") String name) {
        return new Api(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/listTokens")
    public Map<String,String> ListTokens() {
      return sessionsTokens;
    }

    public boolean validateUser(String email, String sessionId) {
      try {
        return (sessionsTokens.get(email).equals(sessionId)? true : false);
      } catch (NullPointerException ex) {
        return false;
      }
    }

    public void appendToken(String email, String token) {
      sessionsTokens.put(email, token);
    }

    public String fetchCurrentDate() {
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date date = new Date();
      return dateFormat.format(date);
    }
}
