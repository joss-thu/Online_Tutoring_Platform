package de.thu.thutorium.controller;

import de.thu.thutorium.model.Course;
import de.thu.thutorium.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * Retrieves a list of courses that belong to a specified category.
     *
     * @param categoryName The name of the category for which courses are to be retrieved.
     * @return A list of {@link Course} objects that fall under the specified category.
     * This endpoint handles HTTP GET requests and is cross-origin enabled for the frontend.
     */

    @GetMapping("/courses/category/{categoryName}")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public List<Course> getCoursesByCategory(@PathVariable String categoryName) {
        return courseService.findCoursesByCategory(categoryName);
    }
}
