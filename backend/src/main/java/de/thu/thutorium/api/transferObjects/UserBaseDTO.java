package de.thu.thutorium.api.transferObjects;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) that represents the base information of a user in the system.
 *
 * <p>This class is used to transfer common user-related data between layers of the application. It
 * contains fields that are shared among all types of users, such as students and tutors. Specific
 * user types may extend or customize this DTO for additional fields.
 */
@Data
public class UserBaseDTO {
  /**
   * The unique identifier of the user.
   *
   * <p>This field holds the ID of the user, which is typically used to identify the user in the
   * system.
   */
  private Long userId;

  /**
   * The first name of the user.
   *
   * <p>This field stores the first name of the user, which is a part of their personal information.
   */
  private String firstName;

  /**
   * The last name of the user.
   *
   * <p>This field stores the last name of the user, which is another part of their personal
   * information.
   */
  private String lastName;

  /**
   * The email address of the user.
   *
   * <p>This field contains the email address of the user, which is used for communication purposes
   * and may be used as a unique identifier.
   */
  private String email;

  /**
   * A brief description or biography of the user.
   *
   * <p>This field allows the user to provide a short description or biography about themselves.
   * This can be used in different contexts, such as user profiles.
   */
  private String description;

  /**
   * A flag indicating whether the user has been verified.
   *
   * <p>This field indicates if the user's email or identity has been verified. It helps the system
   * distinguish between verified and unverified users.
   */
  private Boolean isVerified;

  /**
   * A flag indicating whether the user's account is enabled.
   *
   * <p>This field represents whether the user's account is enabled or disabled. Disabled accounts
   * cannot perform actions like logging in.
   */
  private Boolean enabled;

  /**
   * The timestamp when the user's account was verified.
   *
   * <p>This field holds the timestamp of when the user was verified. It is only relevant if the
   * {@code isVerified} flag is {@code true}.
   */
  private LocalDateTime verifiedOn;

  /**
   * The roles assigned to the user (commented out in the current version).
   *
   * <p>This field is intended to represent the roles assigned to the user, such as "STUDENT" or
   * "TUTOR". These roles help in determining the access level and permissions of the user in the
   * system. It is currently commented out, but could be added in the future.
   */
  //    private Set<String> roles;
}
