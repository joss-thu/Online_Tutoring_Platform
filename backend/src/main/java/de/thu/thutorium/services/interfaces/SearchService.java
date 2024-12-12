package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.CourseDTO;
import de.thu.thutorium.api.transferObjects.TutorDTO;
import de.thu.thutorium.database.dbObjects.CourseCategoryDBO;

import java.util.List;

/**
 * The {@code SearchService} interface provides methods for searching tutors, courses, and
 * retrieving available categories.
 *
 * <p>It exposes the following functionalities:
 *
 * <ul>
 *   <li>Search tutors by name.
 *   <li>Search courses by name.
 *   <li>Retrieve all available course categories.
 * </ul>
 */
public interface SearchService {
  /**
   * Searches for tutors by the given tutor's name.
   *
   * <p>This method will return a list of {@link de.thu.thutorium.api.transferObjects.UserBaseDTO} objects that match the given tutor
   * name. The search may be case-insensitive and can return partial matches depending on the
   * implementation.
   *
   * @param tutorName the name (or partial name) of the tutor to search for.
   * @return a list of {@link de.thu.thutorium.api.transferObjects.UserBaseDTO} objects representing tutors that match the search
   * criteria. If no tutors are found, an empty list is returned.
   */
  List<TutorDTO> searchTutors(String tutorName);

  /**
   * Searches for courses by the given course name.
   *
   * <p>This method will return a list of {@link CourseDTO} objects that match the given course
   * name. The search can support case-insensitivity and partial name matches depending on the
   * implementation.
   *
   * @param courseName the name (or partial name) of the course to search for.
   * @return a list of {@link CourseDTO} objects representing courses that match the search
   *     criteria. If no courses are found, an empty list is returned.
   */
  List<CourseDTO> searchCourses(String courseName);

  /**
   * Retrieves all available course categories.
   *
   * <p>This method will return a list of {@link CourseCategoryDBO} objects, each representing a
   * distinct category to which courses can belong.
   *
   * @return a list of {@link CourseCategoryDBO} objects representing all available categories. If
   *     no categories exist, an empty list is returned.
   */
  List<CourseCategoryDBO> getAllCategories();
}
