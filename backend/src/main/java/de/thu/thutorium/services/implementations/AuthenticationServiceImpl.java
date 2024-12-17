package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.transferObjects.authentication.LogInRequestTO;
import de.thu.thutorium.api.transferObjects.authentication.LogInResponseTO;
import de.thu.thutorium.api.transferObjects.authentication.RegisterRequestTO;
import de.thu.thutorium.database.dbObjects.RoleDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.RoleRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.security.JwtService;
import de.thu.thutorium.services.interfaces.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the AuthenticationService interface. Provides methods to register and
 * authenticate users and generate JWT tokens.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  /** The authentication manager to authenticate users. */
  private final AuthenticationManager authenticationManager;

  /** The user details tests to load user information. */
  private final UserDetailsService userDetailsService;

  /** The JWT to generate tokens. */
  private final JwtService jwtService;

  /** Password encoder */
  private final PasswordEncoder passwordEncoder;

  private final UserRepository UserRepository;
  private final RoleRepository roleRepository;

  /**
   * Authenticates a user based on the provided login request.
   *
   * @param loginRequestTO the login request transfer object containing username and password
   * @return a ResponseEntity containing the authentication response transfer object with the JWT
   *     token and expiration time
   */
  @Override
  public ResponseEntity<LogInResponseTO> authenticate(@RequestBody LogInRequestTO loginRequestTO) {
    // Authenticate the user
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequestTO.getEmail(), loginRequestTO.getPassword()));
    // Load user details
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestTO.getEmail());
    // Retrieve the user ID from the userDetails object (assuming you have a method to get it)
    Long userId =
        ((UserDBO) userDetails)
            .getUserId(); // Assuming CustomUserDetails implements UserDetails and has getId()
    // Generate JWT token
    String jwtToken = jwtService.generateToken(userId, userDetails);
    // Set expiration time (e.g., 24 hours)
    long expiresIn = jwtService.getExpirationTime();
    // Create the response
    LogInResponseTO response =
        new LogInResponseTO(jwtToken, LocalDateTime.now(ZoneOffset.UTC), expiresIn);
    return ResponseEntity.ok(response);
  }

  /**
   * Registers a user based on the provided login request. The code checks if the username (email)
   * is registered under the specified role. If a username does not exist with the specified role it
   * is saved in the database
   *
   * @param registerRequestTO the register request transfer object containing username and password.
   * @return a ResponseEntity containing the register response transfer object with the JWT token
   *     and expiration time
   */
  @Override
  @Transactional
  public ResponseEntity<LogInResponseTO> register(RegisterRequestTO registerRequestTO) {
    // Check if the email is already registered with the specified role
    String email = registerRequestTO.email();
    RoleDBO requestedRole = roleRepository.findByRoleName(registerRequestTO.role());
    Set<RoleDBO> roles = new HashSet<>();
    roles.add(requestedRole);
    UserDBO existingUser = UserRepository.findByEmailAndRoles(email, roles);
    if (existingUser != null) {
      // Email is already registered with the specified role
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new LogInResponseTO("Email is already registered with the specified role"));
    }
    // Fetch existing user or create a new one
    UserDBO user =
        UserRepository.findByEmail(email)
            .orElseGet(
                () ->
                    UserDBO.builder()
                        .firstName(registerRequestTO.firstName())
                        .lastName(registerRequestTO.lastName())
                        .email(email)
                        .password(passwordEncoder.encode(registerRequestTO.password()))
                        .roles(new HashSet<>())
                        .build());
    // Add the new role to the user's roles
    user.getRoles().add(requestedRole);
    // Save the user and get the saved entity
    UserDBO savedUser = UserRepository.save(user);
    // Get the user ID of the newly created user
    Long userId = savedUser.getUserId();
    // Load user details
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    // Generate JWT token
    String jwtToken = jwtService.generateToken(userId, userDetails);
    // Set expiration time (e.g., 24 hours)
    long expiresIn = jwtService.getExpirationTime();
    // Create the response
    LogInResponseTO response =
        new LogInResponseTO(jwtToken, LocalDateTime.now(ZoneOffset.UTC), expiresIn);
    return ResponseEntity.ok(response);
  }
}
