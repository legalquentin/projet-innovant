package app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.security.MessageDigest;
import java.security.Security;

// this class is used to serialize incoming data from http requests
// and create a ResponseBody for the client

public class Serializer {

  String SerializeUser(User user) {
    return "chuck norris";
  }

  String CreateResponseBody(int status, String response) {
    try {
      JSONObject obj = new JSONObject();
      obj.put("Status", status);
      obj.put("Response", response);
      return obj.toJSONString();
    } catch (Exception ex) {
      ex.printStackTrace();
      return CreateResponseBody(500, "Internal Server Error");
    }
  }

  String encodePwd(String message) {
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
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return generatedPassword;
  }
}
