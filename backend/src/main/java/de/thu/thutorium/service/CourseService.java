package de.thu.thutorium.service;

import de.thu.thutorium.model.Course;
import de.thu.thutorium.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    /**
     * Fetches a list of courses from the repository that match the specified category.
     *
     * @param categoryName The category of courses to search for.
     * @return A list of {@link Course} entities belonging to the specified category.
     * This method delegates the database query to the repository layer.
     */

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findCoursesByCategory(String category) {
        return courseRepository.findByCategory(category);
    }
}

