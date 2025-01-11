package de.thu.thutorium;

import de.thu.thutorium.api.controllers.CourseController;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.services.interfaces.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    public CourseControllerTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGetCourseById_Success() {
        // Given
        Long courseId = 1L;
        CourseTO mockCourse = new CourseTO(
                courseId,
                "Test Course",
                2L,
                "Short description",
                "Long description",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                4.5
        );

        when(courseService.findCourseById(courseId)).thenReturn(mockCourse);

        // When
        CourseTO result = courseController.getCourseById(courseId);

        // Then
        assertNotNull(result);
        assertEquals(courseId, result.getCourseId());
        assertEquals("Test Course", result.getCourseName());
        assertEquals(2L, result.getTutorId());
        verify(courseService, times(1)).findCourseById(courseId);
    }

    @Test
    void testGetCourseById_NotFound() {
        // Given
        Long courseId = 2L;
        when(courseService.findCourseById(courseId)).thenReturn(null);

        // When
        CourseTO result = courseController.getCourseById(courseId);

        // Then
        assertNull(result);
        verify(courseService, times(1)).findCourseById(courseId);
    }

    @Test
    void testGetCourseById_InternalServerError() {
        // Given
        Long courseId = 3L;
        when(courseService.findCourseById(courseId)).thenThrow(new RuntimeException("Unexpected error"));

        // When
        Exception exception = assertThrows(RuntimeException.class, () -> courseController.getCourseById(courseId));

        // Then
        assertEquals("Unexpected error", exception.getMessage());
        verify(courseService, times(1)).findCourseById(courseId);
    }
}
