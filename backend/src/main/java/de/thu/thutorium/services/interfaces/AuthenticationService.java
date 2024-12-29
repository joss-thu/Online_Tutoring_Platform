package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.authentication.LogInRequestTO;
import de.thu.thutorium.api.transferObjects.authentication.LogInResponseTO;
import de.thu.thutorium.api.transferObjects.authentication.RegisterRequestTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AuthenticationService is an interface for the AuthenticationController.
 *
 * <p>This service provides methods to register and authenticate users in the system.
 */
public interface AuthenticationService {
  /**
   * Authenticates a user based on the provided login request.
   *
   * <p>This method validates the user's credentials and returns an authentication response
   * containing a token if the credentials are correct.
   *
   * @param loginRequestTO the login request containing user credentials.
   * @return a {@link ResponseEntity} containing the authentication response {@link
   *     LogInResponseTO}.
   */
  ResponseEntity<LogInResponseTO> authenticate(@RequestBody LogInRequestTO loginRequestTO);

  /**
   * Registers a new user based on the provided registration request.
   *
   * <p>This method processes the registration details, validates the input, and creates a new user
   * in the system.
   *
   * @param registerRequestTO the register request containing user credentials and registration
   *     data.
   * @return a {@link ResponseEntity} containing the authentication response {@link
   *     LogInResponseTO}, which may include a token for the newly registered user.
   */
  ResponseEntity<LogInResponseTO> register(@RequestBody RegisterRequestTO registerRequestTO);
}
