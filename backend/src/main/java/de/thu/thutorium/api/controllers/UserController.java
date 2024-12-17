package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.UserTO;
import de.thu.thutorium.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  private final UserService userService;

  /**
   * Constructs a new {@code UserController} with the specified {@code UserService}.
   *
   * @param userService the service used to access user data
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retrieves the account details of a user based on their user ID.
   *
   * @param userId the unique identifier of the user, extracted from the query parameter
   * @return the {@link UserTO} object containing the account details
   * @throws IllegalArgumentException if {@code userId} is null
   * @see UserService#findByUserId(Long)
   */
  @GetMapping("account")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public UserTO getAccount(@RequestParam Long userId) {
    return userService.findByUserId(userId);
  }
}
