package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.UniversityDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link UniversityDBO}
 * entities in the database.
 * This interface extends {@link JpaRepository}, providing CRUD operations and
 * additional query methods
 * for the {@code UniversityDBO} entity.
 * <p>
 * The {@code JpaRepository} interface provides methods such as {@code save},
 * {@code findById}, {@code findAll},
 * {@code deleteById}, and many more, allowing for easy interaction with the
 * database.
 * </p>
 */
@Repository
public interface UniversityRepository extends JpaRepository<UniversityDBO, Long> {
}
