package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.ProgressDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<ProgressDBO, Long> {

    @Query("SELECT p FROM ProgressDBO p WHERE p.student.userId = :userId AND p.course.courseId = :courseId")
    ProgressDBO findByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
