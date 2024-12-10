package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.authentication.LogInRequestTO;
import de.thu.thutorium.api.transferObjects.authentication.LogInResponseTO;
import de.thu.thutorium.api.transferObjects.authentication.RegisterRequestTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AuthenticationService is an interface for the AuthenticationController .
 * It provides methods to register and authenticate users.
 */
public interface AuthenticationService {
    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequestTO the login request containing user credentials.
     * @return a ResponseEntity containing the authentication response.
     */
    ResponseEntity<LogInResponseTO> authenticate(@RequestBody LogInRequestTO loginRequestTO);

    /**
     * Register a user based on the provided login request.
     *
     * @param registerRequestTO the register request containing user credentials.
     * @return a ResponseEntity containing the authentication response.
     */
    ResponseEntity<LogInResponseTO> register(@RequestBody RegisterRequestTO registerRequestTO);
}
