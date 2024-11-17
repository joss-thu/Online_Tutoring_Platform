package de.thu.thutorium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.thu.thutorium.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
