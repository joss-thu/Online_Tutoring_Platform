package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
