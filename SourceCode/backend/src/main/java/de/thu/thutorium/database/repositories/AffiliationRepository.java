package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.AffiliationDBO;
import de.thu.thutorium.database.dbObjects.enums.AffiliationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link AffiliationDBO} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing CRUD (Create, Read, Update, Delete)
 * operations and query methods for {@link AffiliationDBO} entities, which represent an affiliation
 * between a user and a university.
 *
 * <p>The repository is annotated with {@link Repository}, which indicates that it is a Spring Data
 * JPA repository and should be managed by the Spring container.
 *
 * <p>Custom query methods can be added here if needed, leveraging Spring Data JPA's query creation
 * mechanism.
 */
@Repository
public interface AffiliationRepository extends JpaRepository<AffiliationDBO, Long> {
  Optional<AffiliationDBO> findByAffiliationTypeAndUniversity_UniversityName(
      AffiliationType affiliationType, String universityName);
}
