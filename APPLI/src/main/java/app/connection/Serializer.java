package app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
}
