package de.thu.thutorium.controller;

import de.thu.thutorium.model.Course;
import de.thu.thutorium.service.CourseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling HTTP requests related to courses. /
 */
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * Retrieves a list of courses taught by a specific tutor. The search can be based on either the
     * tutor's full name or by first and last names.
     *
     * @param firstName the first name of the tutor (optional).
     * @param lastName  the last name of the tutor (optional).
     * @param tutorName the full name of the tutor (optional).
     * @return a list of {@link Course} objects taught by the specified tutor.
     */
    @GetMapping("/courses/tutor")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public List<Course> getCoursesByTutor(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String tutorName) {

        // If firstName and lastName are provided, search by both first and last names
        if (firstName != null && lastName != null) {
            return courseService.findCoursesByTutorName(firstName, lastName);
        }

        // If tutorName is provided, search by the full name
        if (tutorName != null) {
            return courseService.findCoursesByFullTutorName(tutorName);
        }

        // If no parameters are provided, return an empty list
        return List.of();
    }

    /**
     * Retrieves a list of courses based on a partial name match.
     *
     * @param name The partial name of the course to search for.
     * @return A list of {@link Course} objects that match the specified partial name.
     */
    @GetMapping("/courses/search")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public List<Course> getCoursesByName(@RequestParam("name") String name) {
        return courseService.findCoursesByName(name);
    }

    /**
     * Retrieves the total count of courses in the system.
     *
     * <p>This endpoint is exposed as a GET request at "/courses/count" and is
     * accessible from the frontend running on <code>http://localhost:3000</code>.
     * The <code>@CrossOrigin</code> annotation allows cross-origin requests from
     * this origin with a maximum age of 3600 seconds for preflight caching.
     *
     * @return the total number of courses available in the system.
     * @apiNote This method delegates the operation to the {@link CourseService#countCourses()} method.
     * @example // Example usage:
     * // HTTP GET request to http://localhost:8080/courses/count
     * // Response: 42
     */

    @GetMapping("/courses/count")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public long getCoursesCount() {
        return courseService.countCourses();
    }
}
