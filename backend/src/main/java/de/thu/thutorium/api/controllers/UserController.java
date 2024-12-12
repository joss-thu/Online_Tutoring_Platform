package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.UserBaseDTO;
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
   * Endpoint to get the total count of students.
   *
   * @return the total number of users with the role of 'student'
   * @apiNote This endpoint can be accessed via a GET request to '/students/count'.
   * @example GET /students/count
   * @response 42
   */
  @GetMapping("students/count")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public Long getStudentCount() {
    return userService.getStudentCount();
  }

  /**
   * Endpoint to get the total count of tutors.
   *
   * @return the total number of users with the role of 'tutor'
   * @apiNote This endpoint can be accessed via a GET request to '/tutors/count'.
   * @example GET /tutors/count
   * @response 15
   */
  @GetMapping("tutors/count")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public Long getTutorsCount() {
    return userService.getTutorCount();
  }

  /**
   * Retrieves the account details of a user based on their user ID.
   *
   * @param userId the unique identifier of the user, extracted from the query parameter
   * @return the {@link UserBaseDTO} object containing the account details
   * @throws IllegalArgumentException if {@code userId} is null
   * @see UserService#findByUserId(Long)
   */
  @GetMapping("account")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public UserBaseDTO getAccount(@RequestParam Long userId) {
    return userService.findByUserId(userId);
  }

  /**
   * Retrieves the details of a tutor based on their unique ID.
   *
   * @param id the unique identifier of the tutor, provided as a query parameter
   * @return the {@link UserBaseDTO} object representing the tutor
   * @see UserService#getTutorByID(Long)
   */
  @GetMapping("tutor")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public UserBaseDTO getTutor(@RequestParam Long id) {
    return userService.getTutorByID(id);
  }
}
