package de.thu.thutorium.service;

import de.thu.thutorium.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing course-related operations.
 *
 * <p>This service provides methods to fetch counts of courses and perform operations related to courses.
 */
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    /**
     * Constructs a new {@code CourseService} with the specified {@code CourseRepository}.
     *
     * @param courseRepository the repository used to access course data
     */
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Gets the total count of courses.
     *
     * @return the total number of courses in the system
     * @apiNote This method uses the {@link CourseRepository#count()} method.
     * @example getTotalCourseCount() // returns 25
     */
    public Long getTotalCourseCount() {
        return courseRepository.count();
    }

//    /**
//     * Gets the total count of courses by category.
//     *
//     * @param category the category of courses to count
//     * @return the total number of courses in the specified category
//     * @apiNote This method uses the {@link CourseRepository#countByCategory(String)} method.
//     * @example getCourseCountByCategory("Math") // returns 10
//     */
//    public Long getCourseCountByCategory(String category) {
//        return courseRepository.countByCategory(category);
//    }
}
