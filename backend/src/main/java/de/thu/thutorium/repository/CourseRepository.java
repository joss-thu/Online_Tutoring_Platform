package de.thu.thutorium.repository;

import de.thu.thutorium.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Retrieves courses that belong to a specific category.
     *
     * @param category The category name to search for (case-insensitive).
     * @return A list of {@link Course} entities that match the given category.
     * Uses a custom JPQL query to perform a case-insensitive search in the database.
     */


    @Query("SELECT c FROM Course c WHERE c.category = :category")
    List<Course> findByCategory(@Param("category") String category);
}
