package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.frontendMappers.UserMapper;
import de.thu.thutorium.api.transferObjects.common.UserTO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.dbObjects.enums.Role;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} interface that provides methods for retrieving and
 * managing user data, including retrieving counts of students and tutors, and fetching user details
 * by their unique identifiers.
 *
 * <p>This service interacts with the {@link UserRepository} to retrieve user data and utilizes
 * {@link UserMapper} to map data between {@link UserDBO} and {@link UserTO}.
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  /**
   * Returns the total number of students in the system.
   *
   * <p>This method queries the {@link UserRepository} to count all users with the role "STUDENT".
   *
   * @return the total number of students as a {@code Long}.
   */
  @Override
  public Long getStudentCount() {
    return userRepository.findAll().stream()
        .filter(
            user ->
                user.getRoles().stream().anyMatch(role -> role.getRoleName().equals(Role.STUDENT)))
        .count();
  }

  /**
   * Returns the total number of tutors in the system.
   *
   * <p>This method queries the {@link UserRepository} to count all users with the role "TUTOR".
   *
   * @return the total number of tutors as a {@code Long}.
   */
  @Override
  public Long getTutorCount() {
    return userRepository.findAll().stream()
        .filter(
            user ->
                user.getRoles().stream().anyMatch(role -> role.getRoleName().equals(Role.TUTOR)))
        .count();
  }

  /**
   * Finds a user by their unique user ID.
   *
   * <p>This method fetches the {@link UserDBO} object from the {@link UserRepository} using the
   * provided {@code userId} and maps it to a {@link UserTO} using the {@link UserMapper}. If no
   * user is found, {@code null} is returned.
   *
   * @param userId the unique ID of the user to retrieve.
   * @return a {@link UserTO} representing the user, or {@code null} if no user is found.
   */
  @Override
  public UserTO findByUserId(Long userId) {
    // Fetch UserDBO from the repository
    UserDBO user = userRepository.findByUserId(userId);

    if (user != null) {
      // Map UserDBO to UserBaseDTO
      return userMapper.toDTO(user);
    }
    return null;
  }

  /**
   * Finds a tutor by their unique tutor ID.
   *
   * <p>This method fetches the {@link UserDBO} object from the {@link UserRepository} using the
   * provided {@code tutorId} and maps it to a {@link UserTO} using the {@link UserMapper}. If no
   * tutor is found, {@code null} is returned.
   *
   * @param tutorId the unique ID of the tutor to retrieve.
   * @return a {@link UserTO} representing the tutor, or {@code null} if no tutor is found.
   */
  @Override
  public UserTO getTutorByID(Long tutorId) {
    UserDBO user = userRepository.findByTutorId(tutorId);

    if (user != null) {
      return userMapper.toDTO(user);
    }
    return null;
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    // Check if the user exists before attempting to delete
    UserDBO user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

    // Delete the user from the repository
    userRepository.delete(user);
  }
}
