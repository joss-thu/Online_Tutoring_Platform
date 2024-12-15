package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.TutorTO;
import de.thu.thutorium.database.dbObjects.CourseCategoryDBO;
import de.thu.thutorium.services.implementations.SearchServiceImpl;
import de.thu.thutorium.services.interfaces.CourseService;
import de.thu.thutorium.services.interfaces.SearchService;
import de.thu.thutorium.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing search functionalities for tutors, courses, and general information.
 *
 * <p>This controller provides endpoints that allow users to:
 *
 * <ul>
 *   <li>Search for tutors by name
 *   <li>Search for courses by name
 *   <li>Retrieve a list of course categories
 *   <li>Get a list of courses by category
 *   <li>Retrieve the total count of students, tutors, and courses on the platform
 * </ul>
 *
 * <p><b>Access:</b> This controller is publicly accessible, meaning it can be accessed without
 * authentication. It is designed to provide search functionality and general platform information
 * to both logged-in and not logged-in users.
 *
 * <p>All endpoints in this controller support cross-origin requests from "http://localhost:3000"
 * and allow preflight request caching for up to 3600 seconds.
 */
@RestController
@RequestMapping("/search")
public class SearchController {

  private final SearchService searchService;
  private final CourseService courseService;
  private final UserService userService;

  @Autowired
  public SearchController(
      SearchServiceImpl searchServiceImpl, CourseService courseService, UserService userService) {
    this.searchService = searchServiceImpl;
    this.courseService = courseService;
    this.userService = userService;
  }

  /**
   * Searches for tutors or courses based on the provided query parameters.
   *
   * <p>This endpoint supports searching for tutors by name and courses by name. If both parameters
   * are provided, the search will return combined results of matching tutors and courses.
   *
   * @param tutorName Optional. The name of the tutor to search for. If null or empty, tutor search
   *     is skipped.
   * @param courseName Optional. The name of the course to search for. If null or empty, course
   *     search is skipped.
   * @return A list of search results containing either tutors (UserBaseDTO), courses (CourseDTO),
   *     or both, depending on the provided parameters. Results may include duplicates if multiple
   *     entities match the search criteria.
   */
  @GetMapping
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public List<Object> search(
      @RequestParam(required = false) String tutorName,
      @RequestParam(required = false) String courseName) {
    // Initialize an empty list to store results
    List<Object> results = new ArrayList<>();

    // If tutorName is provided, search for tutors and add to the results
    if (tutorName != null && !tutorName.isEmpty()) {
      List<TutorTO> tutors = searchService.searchTutors(tutorName);
      results.addAll(tutors); // Add tutors to the results list
    }

    // If courseName is provided, search for courses and add to the results
    if (courseName != null && !courseName.isEmpty()) {
      List<CourseTO> courses = searchService.searchCourses(courseName);
      results.addAll(courses); // Add courses to the results list
    }

    // Return the combined results without removing duplicates
    return results;
  }

  /**
   * Converts a {@link CourseCategoryDBO} (representing a course category in the database) to a
   * {@link de.thu.thutorium.api.transferObjects.common.CourseCategoryTO}.
   *
   * <p>This method maps the {@code categoryName} field of the {@code CourseCategoryDBO} to the
   * {@code categoryName} field in the {@code CourseCategoryDTO}.
   *
   * @return a {@code CourseCategoryDTO} object containing the course category data
   */
  //TODO: JSON Serialisation problem with ROLES
  @GetMapping("/categories")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public List<CourseCategoryDBO> getCategories() {
    return searchService.getAllCategories();
  }

  /**
   * Retrieves a list of courses based on the specified category name. This endpoint is cross-origin
   * enabled for requests from "http://localhost:3000" and allows preflight requests to be cached
   * for up to 3600 seconds.
   *
   * @param categoryName The name of the category for which courses are to be retrieved.
   * @return A list of {@link CourseTO} objects that belong to the specified category.
   */
  @GetMapping("/category/{categoryName}")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public List<CourseTO> getCoursesByCategory(@PathVariable String categoryName) {
    return courseService.getCoursesByCategory(categoryName);
  }

  // Count Functions (works)
  /**
   * Endpoint to get the total count of students.
   *
   * @return the total number of users with the role of 'student'
   * @apiNote This endpoint can be accessed via a GET request to '/students/count'.
   * @example GET /students/count
   * @response 42
   */
  @GetMapping("students/count")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public Long getStudentCount() {
    return userService.getStudentCount();
  }

  /**
   * Endpoint to get the total count of tutors.
   *
   * @return the total number of users with the role of 'tutor'
   * @apiNote This endpoint can be accessed via a GET request to '/tutors/count'.
   * @example GET /tutors/count
   * @response 15
   */
  @GetMapping("tutors/count")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public Long getTutorsCount() {
    return userService.getTutorCount();
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
  }
}
