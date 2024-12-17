package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.RoleDBO;
import de.thu.thutorium.database.dbObjects.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for RoleDBO entities.
 * Provides methods for performing CRUD operations on role data.
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleDBO, Long> {
    /**
     * Checks if a role entity exists with the given name.
     *
     * @param roleName the role to check
     * @return true if a role entity exists with the given name, false otherwise
     */
    boolean existsByRoleName(Role roleName);

    /**
     * Finds a role entity by its name.
     *
     * @param roleName the name of the role to find
     * @return the found RoleDBO entity
     */
    RoleDBO findByRoleName(Role roleName);
}
