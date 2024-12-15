package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.frontendMappers.CourseMapper;
import de.thu.thutorium.api.frontendMappers.TutorMapper;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.TutorTO;
import de.thu.thutorium.database.dbObjects.CourseCategoryDBO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.CategoryRepository;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.interfaces.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link SearchService} interface that provides methods for searching tutors,
 * courses, and retrieving all available categories.
 *
 * <p>This service interacts with repositories to fetch relevant data and uses mappers to convert
 * database objects into transfer objects for further use in the application.
 */
@Service
public class SearchServiceImpl implements SearchService {
  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final UserRepository userRepository;
  private final TutorMapper tutorMapper;
  private final CategoryRepository categoryRepository;

  public SearchServiceImpl(
      CourseRepository courseRepository,
      CourseMapper courseMapper,
      UserRepository userRepository,
      TutorMapper tutorMapper,
      CategoryRepository categoryRepository) {
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
    this.userRepository = userRepository;
    this.tutorMapper = tutorMapper;
    this.categoryRepository = categoryRepository;
  }

  /**
   * Searches for tutors based on their full name.
   *
   * <p>This method fetches the list of tutors whose full name matches the given {@code tutorName}.
   * The search may support partial matches depending on the implementation. The result is mapped
   * into a list of {@link de.thu.thutorium.api.transferObjects.common.UserTO} objects.
   *
   * @param tutorName the full name of the tutor (can be partial).
   * @return a list of {@link de.thu.thutorium.api.transferObjects.common.UserTO} objects
   *     representing the tutors that match the search criteria. If no tutors are found, an empty
   *     list is returned.
   */
  @Override
  public List<TutorTO> searchTutors(String tutorName) {
    List<UserDBO> tutors = userRepository.findByTutorFullName(tutorName);
    return tutorMapper.toDTOList(tutors);
  }

  /**
   * Searches for courses based on the course name.
   *
   * <p>This method fetches the list of courses whose names match the given {@code courseName}. The
   * search may support partial matches depending on the implementation. The result is mapped into a
   * list of {@link CourseTO} objects.
   *
   * @param courseName the name of the course (can be partial).
   * @return a list of {@link CourseTO} objects representing the courses that match the search
   *     criteria. If no courses are found, an empty list is returned.
   */
  @Override
  public List<CourseTO> searchCourses(String courseName) {
    List<CourseDBO> courses = courseRepository.findCourseByName(courseName);
    return courseMapper.toDTOList(courses);
  }

  /**
   * Retrieves all available course categories.
   *
   * <p>This method fetches the list of all course categories from the {@link CategoryRepository}.
   * The result is a list of {@link CourseCategoryDBO} objects representing the available
   * categories.
   *
   * @return a list of {@link CourseCategoryDBO} objects representing all available categories. If
   *     no categories are found, an empty list is returned.
   */
  @Override
  public List<CourseCategoryDBO> getAllCategories() {
    return categoryRepository.findAllCategories();
  }
}
