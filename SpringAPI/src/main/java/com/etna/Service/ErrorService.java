package com.etna.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

/**
 * Created by quentin on 21/07/2017.
 */
public class ErrorService {

    private static final Logger logger = Logger.getLogger(OfferService.class.getName());

    public static ResponseEntity<Object> unauthenticatedJson() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"response\": \"UNAUTHORIZED: Unauthenticated session-id\"}");
    }

    public static ResponseEntity<Object> invalidJsonRequest(String message) {
        logger.warning(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"response\": \"BAD_REQUEST: JSON payload is invalid \"}");
    }

    public static ResponseEntity<Object> standardError(String message) {
        logger.warning(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"response\": \"INTERNAL_SERVER_ERROR: something went wrong, Oups\"}");
    }
}
