package de.thu.thutorium;

import de.thu.thutorium.database.dbObjects.RoleDBO;
import de.thu.thutorium.database.dbObjects.enums.Role;
import de.thu.thutorium.database.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class initializes the database with default roles.
 *
 */
@Transactional
@Component
@Slf4j
public class DBInitializer {
    private final RoleRepository roleRepository;

    /**
     * Constructor for DBInitializer.
     *
     * @param roleRepository the repository for role data access
     */
    @Autowired
    public DBInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Initializes the database with default roles.
     * This method is called after the bean's properties have been set.
     */
    @PostConstruct
    public void init() {
        // Check if all roles already exist
        if (roleRepository.existsByRoleName(Role.ADMIN) &&
            roleRepository.existsByRoleName(Role.VERIFIER) &&
            roleRepository.existsByRoleName(Role.TUTOR) &&
            roleRepository.existsByRoleName(Role.STUDENT)) {
                return; // Exit if the roles already exist
        } else {
            // Create roles if they don't exist
            RoleDBO adminRole = new RoleDBO(Role.ADMIN);
            RoleDBO studentRole = new RoleDBO(Role.STUDENT);
            RoleDBO tutorRole = new RoleDBO(Role.TUTOR);
            RoleDBO verifierRole = new RoleDBO(Role.VERIFIER);

            roleRepository.save(adminRole);
            roleRepository.save(studentRole);
            roleRepository.save(tutorRole);
            roleRepository.save(verifierRole);
        }
    }
}
