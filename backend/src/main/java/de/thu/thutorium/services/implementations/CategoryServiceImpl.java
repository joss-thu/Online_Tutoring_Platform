package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.frontendMappers.CourseCategoryMapper;
import de.thu.thutorium.api.transferObjects.common.CourseCategoryTO;
import de.thu.thutorium.database.dbObjects.CourseCategoryDBO;
import de.thu.thutorium.database.repositories.CategoryRepository;
import de.thu.thutorium.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository courseCategoryRepository;
    private final CourseCategoryMapper courseCategoryMapper;

    public CategoryServiceImpl(CategoryRepository courseCategoryRepository, CourseCategoryMapper courseCategoryMapper) {
        this.courseCategoryRepository = courseCategoryRepository;
        this.courseCategoryMapper = courseCategoryMapper;
    }

    /**
     * Creates a new course category.
     *
     * @param courseCategoryTO The details of the course category to be created.
     * @return The created course category as a DTO.
     */
    public CourseCategoryTO createCourseCategory(CourseCategoryTO courseCategoryTO) {
        CourseCategoryDBO courseCategoryDBO = courseCategoryMapper.toEntity(courseCategoryTO);
        courseCategoryDBO.setCreatedOn(LocalDateTime.now());
        // Assuming `createdBy` is set elsewhere, e.g., via security context or passed explicitly.
        CourseCategoryDBO savedCategory = courseCategoryRepository.save(courseCategoryDBO);
        return courseCategoryMapper.toDTO(savedCategory);
    }
}
