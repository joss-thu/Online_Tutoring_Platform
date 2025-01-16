package de.thu.thutorium.Utility;

import de.thu.thutorium.database.dbObjects.UserDBO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for authentication-related operations.
 */
public class AuthUtil {
    /**
     * Retrieves the authenticated user's ID from the security context.
     *
     * <p>
     * This method fetches the current authentication object from the security
     * context,
     * checks if the user is authenticated, and then extracts the user ID from the
     * principal.
     * If the user is not authenticated, an {@link AuthenticationException} is
     * thrown.
     *
     * @return the authenticated user's ID
     * @throws AuthenticationException if the user is not authenticated
     */
    public static Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User is not authenticated") {
            };
        }
        UserDBO userDetails = (UserDBO) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}
