package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.CourseTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code CourseService} interface provides methods for retrieving and searching courses.
 *
 * <p>It exposes the following functionalities:
 *
 * <ul>
 *   <li>Find a course by its ID.
 *   <li>Find courses by tutor's first and last name.
 *   <li>Find courses by a full tutor's name.
 *   <li>Find courses by course name.
 *   <li>Retrieve courses by category name.
 *   <li>Get the total count of courses.
 * </ul>
 */
@Service
public interface CourseService {
  /**
   * Finds a course by its unique ID.
   *
   * <p>This method returns the {@link CourseTO} object corresponding to the given course ID. If no
   * course is found for the provided ID, it returns {@code null}.
   *
   * @param id the unique ID of the course to retrieve.
   * @return the {@link CourseTO} object representing the course with the specified ID, or {@code
   *     null} if no course with the given ID exists.
   */
  CourseTO findCourseById(Long id);

  /**
   * Finds courses taught by a tutor with the specified first and last name.
   *
   * <p>This method will return a list of {@link CourseTO} objects that match the provided tutor's
   * first and last name. The search may be case-insensitive and can return partial matches.
   *
   * @param firstName the first name of the tutor.
   * @param lastName the last name of the tutor.
   * @return a list of {@link CourseTO} objects representing the courses taught by the specified
   *     tutor. If no courses are found, an empty list is returned.
   */
  List<CourseTO> findCoursesByTutorName(String firstName, String lastName);

  /**
   * Finds courses taught by a tutor with the specified full name.
   *
   * <p>This method will return a list of {@link CourseTO} objects that match the provided full
   * tutor's name. The search may be case-insensitive and can return partial matches.
   *
   * @param tutorName the full name of the tutor.
   * @return a list of {@link CourseTO} objects representing the courses taught by the tutor with
   *     the specified name. If no courses are found, an empty list is returned.
   */
  List<CourseTO> findCoursesByFullTutorName(String tutorName);

  /**
   * Finds courses by the given course name.
   *
   * <p>This method will return a list of {@link CourseTO} objects that match the given course name.
   * The search may support case-insensitivity and partial name matches depending on the
   * implementation.
   *
   * @param name the name (or partial name) of the course to search for.
   * @return a list of {@link CourseTO} objects representing courses that match the search criteria.
   *     If no courses are found, an empty list is returned.
   */
  List<CourseTO> findCoursesByName(String name);

  /**
   * Retrieves courses that belong to a specific category.
   *
   * <p>This method returns a list of {@link CourseTO} objects that belong to the given category.
   *
   * @param categoryName the name of the category to search for.
   * @return a list of {@link CourseTO} objects representing courses in the specified category. If
   *     no courses are found for the given category, an empty list is returned.
   */
  List<CourseTO> getCoursesByCategory(String categoryName);

  /**
   * Gets the total count of courses available.
   *
   * <p>This method returns the total number of courses available in the system.
   *
   * @return the total number of courses as a {@code Long} value.
   */
  Long getTotalCountOfCourses();

  void createCourse(CourseTO courseTO);

  void deleteCourse(Long courseId);

  void updateCourse(Long courseId, CourseTO courseTO);
}
