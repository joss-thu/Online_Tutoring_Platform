package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Data Transfer Object (DTO) that represents the base information of a user in the system.
 *
 * <p>This class is used to transfer common user-related data between layers of the application. It
 * contains fields that are shared among all types of users, such as students and tutors. Specific
 * user types may extend or customize this DTO for additional fields.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTO {
    /**
     * The unique identifier of the user.
     *
     * <p>This field holds the ID of the user, which is typically used to identify the user in the
     * system.
     */
    @NotNull(message = "User id cannot be null")
    private Long userId;

    /**
     * The first name of the user.
     *
     * <p>This field stores the first name of the user, which is a part of their personal information.
     */
    @NotEmpty(message = "The firstname cannot be empty")
    private String firstName;

    /**
     * The last name of the user.
     *
     * <p>This field stores the last name of the user, which is another part of their personal
     * information.
     */
    @NotEmpty(message = "The last name cannot be empty")
    private String lastName;

    /**
     * The email address of the user.
     *
     * <p>This field contains the email address of the user, which is used for communication purposes
     * and may be used as a unique identifier.
     */
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;


    /**
     * The roles assigned to the user (commented out in the current version).
     *
     * <p>This field is intended to represent the roles assigned to the user, such as "STUDENT" or
     * "TUTOR". These roles help in determining the access level and permissions of the user in the
     * system. It is currently commented out, but could be added in the future.
     */
    @NotEmpty(message = "The roles can not be null or empty")
    private Set<String> roles;

    /**
     * The affiliation of the user.
     *
     */
    private AffiliationTO affiliation;
}
