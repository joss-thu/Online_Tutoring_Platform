package de.thu.thutorium.api.transferObjects.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transfer object for Login Request
 * Contains username (email in this context) and password
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequestTO {
    private String username;
    private String password;
}
