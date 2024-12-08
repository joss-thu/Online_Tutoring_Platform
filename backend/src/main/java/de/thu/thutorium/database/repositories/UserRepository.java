package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDBO, Integer> {

}
