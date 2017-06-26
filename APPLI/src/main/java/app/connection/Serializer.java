package app.connection;

import app.user.User;
import org.json.simple.JSONObject;

import java.security.MessageDigest;
<<<<<<< HEAD
import java.security.Security;
import java.security.SecureRandom;
import java.math.BigInteger;
=======
>>>>>>> 68a3baf0f893863c5a6bdfcf80d24f7a9c79f624

// this class is used to serialize incoming data from http requests
// and create a ResponseBody for the client

public class Serializer {

  private SecureRandom random = new SecureRandom();
  private ApiController apiCtrl = new ApiController();

  String SerializeUser(User user) {
    return "chuck norris";
  }

  public String CreateResponseBody(int status, String response) {
    try {
      JSONObject obj = new JSONObject();
      obj.put("status", status);
      obj.put("response", response);
      return obj.toJSONString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return CreateResponseBody(500, "Internal Server Error");
    }
  }

  String encodePwd(String message) {
    System.out.println(message);
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(message.getBytes());
      byte[] bytes = md.digest();
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return generatedPassword;
  }

  public boolean checkToken(String email, String sessionId) {
    return apiCtrl.validateUser(email, sessionId);
  }

  public String bakeToken(String email) {
    String token = new BigInteger(130, random).toString(32);
    apiCtrl.appendToken(email, token);
    return token;
  }
}
