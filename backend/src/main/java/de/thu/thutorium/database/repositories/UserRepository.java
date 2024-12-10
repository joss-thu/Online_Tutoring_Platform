package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.RoleDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for UserDBO entities.
 * Provides methods for performing CRUD operations on user data.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDBO, Integer> {
    /**
     * Finds a user entity by its username; in this case, email.
     *
     * @param email the email to search for
     * @return an Optional containing the found UserDBO, or empty if not found
     */
    Optional<UserDBO> findByEmail(String email);

    /**
     * Checks if a user entity exists with the given email.
     *
     * @param email the username to check
     * @return true if a user entity exists with the given username, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email and roles.
     *
     * @param email the email of the user to find
     * @param roles the roles of the user to find
     * @return the user with the specified email and roles, or {@code null} if no such user exists
     */
    UserDBO findByEmailAndRoles(String email, Set<RoleDBO> roles);
}
