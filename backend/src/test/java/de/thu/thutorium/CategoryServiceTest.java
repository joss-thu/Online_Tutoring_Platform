package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.CourseCategoryTOMapper;
import de.thu.thutorium.api.transferObjects.common.CourseCategoryTO;
import de.thu.thutorium.database.DBOMappers.CourseCategoryDBOMapper;
import de.thu.thutorium.database.dbObjects.CourseCategoryDBO;
import de.thu.thutorium.database.repositories.CategoryRepository;
import de.thu.thutorium.exceptions.ResourceAlreadyExistsException;
import de.thu.thutorium.services.implementations.CategoryServiceImpl;
import de.thu.thutorium.services.interfaces.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CourseCategoryDBOMapper categoryDBOMapper;

    @Mock
    private CourseCategoryTOMapper categoryTOMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourseCategory_Success() {
        // Arrange
        CourseCategoryTO categoryTO = new CourseCategoryTO();
        CourseCategoryDBO categoryDBO = new CourseCategoryDBO();
        CourseCategoryDBO savedCategoryDBO = new CourseCategoryDBO();
        CourseCategoryTO mappedCategoryTO = new CourseCategoryTO();

        when(categoryRepository.findCourseCategoryDBOByCategoryName(categoryTO.getCategoryName()))
                .thenReturn(null);
        when(categoryDBOMapper.toDBO(categoryTO)).thenReturn(categoryDBO);
        when(categoryRepository.save(categoryDBO)).thenReturn(savedCategoryDBO);
        when(categoryTOMapper.toDTO(savedCategoryDBO)).thenReturn(mappedCategoryTO);

        // Act
        CourseCategoryTO result = categoryService.createCourseCategory(categoryTO);

        // Assert
        assertNotNull(result);
        assertEquals(categoryTO.getCategoryName(), result.getCategoryName());
        verify(categoryRepository, times(1))
                .findCourseCategoryDBOByCategoryName(categoryTO.getCategoryName());
        verify(categoryRepository, times(1)).save(categoryDBO);
        verify(categoryDBOMapper, times(1)).toDBO(categoryTO);
        verify(categoryTOMapper, times(1)).toDTO(savedCategoryDBO);
    }

    @Test
    void testCreateCourseCategory_AlreadyExists() {
        // Arrange
        CourseCategoryTO categoryTO = new CourseCategoryTO();
        CourseCategoryDBO existingCategoryDBO = new CourseCategoryDBO();
        existingCategoryDBO.setCategoryName("Programming");

        when(categoryRepository.findCourseCategoryDBOByCategoryName(categoryTO.getCategoryName()))
                .thenReturn(existingCategoryDBO);

        // Act & Assert
        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> categoryService.createCourseCategory(categoryTO)
        );

        assertEquals("Category \"Programming\" already exists!", exception.getMessage());
        verify(categoryRepository, times(1))
                .findCourseCategoryDBOByCategoryName(categoryTO.getCategoryName());
        verifyNoInteractions(categoryDBOMapper, categoryTOMapper);
    }

    @Test
    void testUpdateCourseCategory_NotFound() {
        // Arrange
        int categoryId = 1;
        CourseCategoryTO categoryTO = new CourseCategoryTO();

        when(categoryRepository.findById((long) categoryId)).thenReturn(Optional.empty());

        // Act
        Optional<CourseCategoryTO> result = categoryService.updateCourseCategory(categoryId, categoryTO);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById((long) categoryId);
        verifyNoInteractions(categoryDBOMapper, categoryTOMapper);
    }

    @Test
    void testUpdateCourseCategory_Success() {
        // Arrange
        int categoryId = 1;
        CourseCategoryTO categoryTO = new CourseCategoryTO();
        CourseCategoryDBO existingCategoryDBO = new CourseCategoryDBO();
        CourseCategoryDBO updatedCategoryDBO = new CourseCategoryDBO();
        CourseCategoryTO updatedCategoryTO = new CourseCategoryTO();

        when(categoryRepository.findById((long) categoryId)).thenReturn(Optional.of(existingCategoryDBO));
        when(categoryDBOMapper.toDBO(categoryTO)).thenReturn(updatedCategoryDBO);
        when(categoryRepository.save(updatedCategoryDBO)).thenReturn(updatedCategoryDBO);
        when(categoryTOMapper.toDTO(updatedCategoryDBO)).thenReturn(updatedCategoryTO);

        // Act
        Optional<CourseCategoryTO> result = categoryService.updateCourseCategory(categoryId, categoryTO);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedCategoryTO.getCategoryName(), result.get().getCategoryName());
        verify(categoryRepository, times(1)).findById((long) categoryId);
        verify(categoryDBOMapper, times(1)).toDBO(categoryTO);
        verify(categoryRepository, times(1)).save(updatedCategoryDBO);
        verify(categoryTOMapper, times(1)).toDTO(updatedCategoryDBO);
    }

    @Test
    void testDeleteCourseCategory_NotFound() {
        // Arrange
        int categoryId = 1;

        when(categoryRepository.findById((long) categoryId)).thenReturn(Optional.empty());

        // Act
        assertDoesNotThrow(() -> categoryService.deleteCourseCategory(categoryId));

        // Assert
        verify(categoryRepository, times(1)).findById((long) categoryId);
        verify(categoryRepository, never()).deleteById((long) categoryId);
    }

    @Test
    void testDeleteCourseCategory_Success() {
        // Arrange
        int categoryId = 1;
        CourseCategoryDBO existingCategoryDBO = new CourseCategoryDBO();

        when(categoryRepository.findById((long) categoryId)).thenReturn(Optional.of(existingCategoryDBO));

        // Act
        categoryService.deleteCourseCategory(categoryId);

        // Assert
        verify(categoryRepository, times(1)).findById((long) categoryId);
        verify(categoryRepository, times(1)).deleteById((long) categoryId);
    }
}
