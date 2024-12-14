package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.UserBaseDTO;

public interface UserService {

  /**
   * Gets the total count of users with the role of 'STUDENT'.
   *
   * @return the total number of students in the system
   */
  Long getStudentCount();

  /**
   * Gets the total count of users with the role of 'TUTOR'.
   *
   * @return the total number of tutors in the system
   */
  Long getTutorCount();

  /**
   * Finds and retrieves a user by their unique user ID.
   *
   * @param userId the unique identifier of the user to retrieve
   * @return the {@link de.thu.thutorium.database.dbObjects.UserDBO} object if found
   * @throws IllegalArgumentException if {@code userId} is null
   */
  UserBaseDTO findByUserId(Long userId);

  /**
   * Retrieves a {@link de.thu.thutorium.database.dbObjects.UserDBO} entity representing a tutor by their unique identifier.
   *
   * @param tutorId the unique identifier of the tutor to be retrieved
   * @return the {@link de.thu.thutorium.database.dbObjects.UserDBO} entity corresponding to the specified tutor ID, or {@code null} if
   *     no such tutor is found
   */
  UserBaseDTO getTutorByID(Long tutorId);

  void deleteUser(Long userId);
}
