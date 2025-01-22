package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.ProgressDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link ProgressDBO} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing CRUD (Create, Read, Update, Delete)
 * operations and query methods for {@link ProgressDBO} entities, which represent the progress of a
 * student in a particular course.
 *
 * <p>The repository is annotated with {@link Repository}, indicating that it is a Spring Data JPA
 * repository and should be managed by the Spring container.
 *
 * <p>This interface includes a custom query method {@link #findByUserIdAndCourseId(Long, Long)} for
 * retrieving progress data based on the user's ID and the course's ID.
 *
 * <p>Custom query methods can be added here if needed, leveraging Spring Data JPA's query creation
 * mechanism.
 */
@Repository
public interface ProgressRepository extends JpaRepository<ProgressDBO, Long> {
  /**
   * Finds the progress of a student in a specific course based on the user's ID and the course's
   * ID.
   *
   * @param userId The ID of the student (user).
   * @param courseId The ID of the course.
   * @return The progress entity corresponding to the student and course, or null if no progress
   *     record is found.
   */
  @Query(
      "SELECT p FROM ProgressDBO p WHERE p.student.userId = :userId AND p.course.courseId = :courseId")
  ProgressDBO findByUserIdAndCourseId(
      @Param("userId") Long userId, @Param("courseId") Long courseId);
}
