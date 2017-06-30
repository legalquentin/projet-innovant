package com.etna.Service;

import com.etna.Entity.User;
import com.etna.Repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by quentin on 30/06/2017.
 */

@Service
public class ConnectionService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(ConnectionService.class.getName());

    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final String PWD_PATTERN = "^([a-zA-Z0-9@*#]{6,15})$";
    private static final Map<String, String> sessionsTokens = new HashMap<String, String>();

    public Map<String,String> getSessionsTokens() {
        return sessionsTokens;
    }


    public ResponseEntity<Object> registerUser(User user) {
        try {
            JSONObject json = new JSONObject();

//            Bellow is used to create the table if it does not exist
//            Though i have a hunch on why it's a really bad idea to do that
//            we'll keep it just for the sake of avoiding errors on startup

            userRepository.save(user);
            userRepository.delete(user);

            if(!validator(EMAIL_PATTERN, user.getEmail()) || !validator(PWD_PATTERN, user.getPassword())) {
                json.put("response", "UNPROCESSABLE_ENTITY: User data is invalid");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(json.toString());
            }

            if (userRepository.existsByEmail(user.getEmail())) {
                json.put("response", "FORBIDDEN: Email already registered");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json.toString());
            }
            String pwd = user.getPassword();
            user.setPassword(encodePwd(pwd));
            userRepository.save(user);
            user.setPassword(pwd);
            return authenticateUser(user);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> authenticateUser(User user) {
        try {
            JSONObject json = new JSONObject();
            if(!validator(EMAIL_PATTERN, user.getEmail()) || !validator(PWD_PATTERN, user.getPassword())) {
                json.put("response", "UNPROCESSABLE_ENTITY: User data is invalid");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(json.toString());
            }
            User usr = userRepository.authenticate(user.getEmail(), encodePwd(user.getPassword()));
            if (usr == null) {
                json.put("response", "UNAUTHORIZED: Credentials incorrect");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(json.toString());

            } else {
                json.put("User", usr.getJsonData());
                json.put("session-id", createUserToken(usr.getEmail()));
                return ResponseEntity.status(HttpStatus.OK).body(json.toString());
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public Boolean validator(String expr, String message) {
        try {
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile(expr);
            matcher = pattern.matcher(message);
            return matcher.matches();
        } catch (Exception e) {
            logger.warning("validator error : " + e.getMessage());
            return false;
        }
    }

    public String encodePwd(String message) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            logger.warning("encodePwd error : " + e.getMessage());
        }
        return generatedPassword;
    }

    public boolean validateUser(String email, String sessionId) {
        try {
            return (sessionsTokens.get(email).equals(sessionId)? true : false);
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public String createUserToken(String email) {
        String token = new BigInteger(130, new SecureRandom()).toString(32);
        sessionsTokens.put(email, token);
        return token;
    }

}
