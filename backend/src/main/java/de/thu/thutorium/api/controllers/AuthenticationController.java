package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.authentication.LogInRequestTO;
import de.thu.thutorium.api.transferObjects.authentication.LogInResponseTO;
import de.thu.thutorium.api.transferObjects.authentication.RegisterRequestTO;
import de.thu.thutorium.services.implementations.AuthenticationServiceImpl;
import de.thu.thutorium.services.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController is a REST controller that handles authentication-related requests.
 * It provides endpoints for user login and other authentication operations.
 *
 * This controller uses the AuthenticationService to perform the actual authentication logic.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authService;

    /**
     * Constructs an AuthenticationController with the specified AuthenticationService implementation.
     *
     * @param authenticationServiceImpl the implementation of AuthenticationService to use
     */
    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authService = authenticationServiceImpl;
    }

    /**
     * Registers a user based on the provided register request.
     *
     * @param request the register request transfer object containing user credentials and roles.
     * @return a ResponseEntity containing the login response transfer object
     */
    @PostMapping("/register")
    public ResponseEntity<LogInResponseTO> register(@RequestBody RegisterRequestTO request)
    {
        return authService.register(request);
    }

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param request the login request transfer object containing user credentials
     * @return a ResponseEntity containing the login response transfer object
     */
    @PostMapping("/login")
    public ResponseEntity<LogInResponseTO> authenticate(@RequestBody LogInRequestTO request) {
            return authService.authenticate(request);

    }
}
