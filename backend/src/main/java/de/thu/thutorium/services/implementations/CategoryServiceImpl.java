package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.frontendMappers.CourseCategoryMapper;
import de.thu.thutorium.api.transferObjects.common.CourseCategoryTO;
import de.thu.thutorium.database.dbObjects.CourseCategoryDBO;
import de.thu.thutorium.database.repositories.CategoryRepository;
import de.thu.thutorium.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service implementation for managing course categories.
 *
 * <p>This service provides functionality to create new course categories and interact with the
 * underlying data store. It interacts with the {@link CategoryRepository} to perform database
 * operations and uses the {@link CourseCategoryMapper} to map between the data transfer objects
 * (DTO) and the database objects (DBO).
 *
 * <p>This class provides a method to create a new course category, including setting the creation
 * timestamp.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
  /** Repository for managing course categories. */
  private final CategoryRepository courseCategoryRepository;

  /** Mapper for converting between CourseCategoryDTO and CourseCategoryDBO. */
  private final CourseCategoryMapper courseCategoryMapper;

  /**
   * Constructs a new CategoryServiceImpl with the given repository and mapper.
   *
   * @param courseCategoryRepository the repository for course category database operations
   * @param courseCategoryMapper the mapper for course category data conversion
   */
  public CategoryServiceImpl(
      CategoryRepository courseCategoryRepository, CourseCategoryMapper courseCategoryMapper) {
    this.courseCategoryRepository = courseCategoryRepository;
    this.courseCategoryMapper = courseCategoryMapper;
  }

  /**
   * Creates a new course category.
   *
   * <p>This method maps the provided {@link CourseCategoryTO} to a {@link CourseCategoryDBO}, sets
   * the creation timestamp, saves the entity to the database, and returns the saved category as a
   * DTO.
   *
   * @param courseCategoryTO the transfer object containing the details of the course category to be
   *     created
   * @return the created course category as a {@link CourseCategoryTO} DTO
   */
  public CourseCategoryTO createCourseCategory(CourseCategoryTO courseCategoryTO) {
    CourseCategoryDBO courseCategoryDBO = courseCategoryMapper.toEntity(courseCategoryTO);
    courseCategoryDBO.setCreatedOn(LocalDateTime.now());
    // Assuming `createdBy` is set elsewhere, e.g., via security context or passed explicitly.
    CourseCategoryDBO savedCategory = courseCategoryRepository.save(courseCategoryDBO);
    return courseCategoryMapper.toDTO(savedCategory);
  }
}
