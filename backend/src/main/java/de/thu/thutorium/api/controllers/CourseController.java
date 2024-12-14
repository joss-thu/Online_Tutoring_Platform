package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.services.interfaces.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller class responsible for handling HTTP requests related to courses. */
@RestController
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  /**
   * Retrieves a list of courses taught by a specific tutor. The search can be based on either the
   * tutor's full name or by first and last names.
   *
   * @param firstName the first name of the tutor (optional).
   * @param lastName the last name of the tutor (optional).
   * @param tutorName the full name of the tutor (optional).
   * @return a list of {@link CourseTO} objects taught by the specified tutor.
   */
  @GetMapping("/courses/tutor")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public List<CourseTO> getCoursesByTutor(
          @RequestParam(required = false) String firstName,
          @RequestParam(required = false) String lastName,
          @RequestParam(required = false) String tutorName) {

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
  } // works

  /**
   * Retrieves a list of courses based on a partial name match.
   *
   * @param name The partial name of the course to search for.
   * @return A list of {@link CourseTO} objects that match the specified partial name.
   */
  @GetMapping("/courses/search")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public List<CourseTO> getCoursesByName(@RequestParam("name") String name) {
    return courseService.findCoursesByName(name);
  } // works

  /**
   * Retrieves a course based on its ID.
   *
   * @param id The ID of the course to retrieve.
   * @return The {@link CourseTO} object representing the course.
   */
  @GetMapping("/course")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public CourseTO getCourseById(@RequestParam Long id) {
    return courseService.findCourseById(id);
  } // works

  /**
   * Retrieves a list of courses based on the specified category name. This endpoint is cross-origin
   * enabled for requests from "http://localhost:3000" and allows preflight requests to be cached
   * for up to 3600 seconds.
   *
   * @param categoryName The name of the category for which courses are to be retrieved.
   * @return A list of {@link CourseTO} objects that belong to the specified category.
   */
  @GetMapping("/search/category/{categoryName}")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public List<CourseTO> getCoursesByCategory(@PathVariable String categoryName) {
    return courseService.getCoursesByCategory(categoryName);
  }

  /**
   * Handles a GET request to retrieve the total count of courses. Allows cross-origin requests from
   * "http://localhost:3000" with a maximum age of 3600 seconds.
   *
   * @return the total number of courses as a {@code Long}.
   */
  @GetMapping("/courses/count")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public Long getCoursesCount() {
    return courseService.getTotalCountOfCourses();
  } // works

}
