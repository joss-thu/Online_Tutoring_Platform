package de.thu.thutorium.api.transferObjects.authentication;

import de.thu.thutorium.database.dbObjects.RoleDBO;
import de.thu.thutorium.database.dbObjects.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Transfer object for the user registration request.
 * Contains first name, last name, email, password and role.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
