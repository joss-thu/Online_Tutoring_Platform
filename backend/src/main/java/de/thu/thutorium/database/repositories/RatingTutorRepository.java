package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.RatingTutorDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link RatingTutorDBO} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing CRUD (Create, Read, Update, Delete)
 * operations and query methods for {@link RatingTutorDBO} entities, which represent ratings given
 * by users for tutors.
 *
 * <p>The repository is annotated with {@link Repository}, indicating that it is a Spring Data JPA
 * repository and should be managed by the Spring container.
 *
 * <p>Custom query methods can be added here if needed, leveraging Spring Data JPA's query creation
 * mechanism.
 */
@Repository
public interface RatingTutorRepository extends JpaRepository<RatingTutorDBO, Long> { }
