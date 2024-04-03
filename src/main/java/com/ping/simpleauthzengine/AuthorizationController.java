package com.ping.simpleauthzengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthorizationController {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);
    public AuthorizationEngine internalEngine;

    public AuthorizationController(@Autowired AuthorizationEngine authorizationEngine) {
        this.internalEngine = authorizationEngine;
    }

    @PostMapping("/authorize")
    public ResponseEntity<Void> authorize(@RequestBody Map<String, Object> context) {
        Decision decision = internalEngine.evaluate(context);
        LOGGER.info("{} decision produced for request context {}", decision, context);
        return new ResponseEntity<>(decision == Decision.PERMIT ? HttpStatus.OK : HttpStatus.FORBIDDEN);
    }
}
