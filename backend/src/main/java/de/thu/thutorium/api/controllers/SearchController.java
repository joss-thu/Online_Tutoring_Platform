package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.TutorDTO;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.database.dbObjects.CourseCategoryDBO;
import de.thu.thutorium.services.implementations.SearchServiceImpl;
import de.thu.thutorium.services.interfaces.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/** Controller for searching tutors and courses. */
@RestController
@RequestMapping("/search")
public class SearchController {

  private final SearchService searchService;

  @Autowired
  public SearchController(SearchServiceImpl searchServiceImpl) {
    this.searchService = searchServiceImpl;
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
      List<TutorDTO> tutors = searchService.searchTutors(tutorName);
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
  @GetMapping("/categories")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public List<CourseCategoryDBO> getCategories() {
    return searchService.getAllCategories();
  }
}
