package de.thu.thutorium.contollers;

import de.thu.thutorium.api.controllers.SearchController;
import de.thu.thutorium.api.transferObjects.common.CourseCategoryTO;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.TutorTO;
import de.thu.thutorium.services.interfaces.CourseService;
import de.thu.thutorium.services.interfaces.SearchService;
import de.thu.thutorium.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchCategoryTest {

    @Mock
    private SearchService searchService;

    @Mock
    private CourseService courseService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SearchController searchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchTutorsAndCourses() {
        // Mock data
        TutorTO mockTutor = new TutorTO();
        mockTutor.setLastName("John");

        CourseTO mockCourse = new CourseTO();
        mockCourse.setCourseName("Java");

        when(searchService.searchTutors("John")).thenReturn(Collections.singletonList(mockTutor));
        when(courseService.searchCourses("Java")).thenReturn(Collections.singletonList(mockCourse));

        // Call the search method
        ResponseEntity<?> response = searchController.search("John", "Java");
        List<Object> results = (List<Object>) response.getBody();

        // Verify
        assertNotNull(results);
        assertEquals(2, results.size());
        assertTrue(results.contains(mockTutor));
        assertTrue(results.contains(mockCourse));

        verify(searchService, times(1)).searchTutors("John");
        verify(courseService, times(1)).searchCourses("Java");
    }

    @Test
    void testSearchEmptyParameters() {
        // Call the search method with empty parameters
        ResponseEntity<?> response = searchController.search(null, null);
        List<Object> results = (List<Object>) response.getBody();

        // Verify
        assertNotNull(results);
        assertTrue(results.isEmpty());

        verifyNoInteractions(searchService);
    }

    @Test
    void testGetCoursesByCategory() {
        // Mock data
        CourseTO mockCourse = new CourseTO();
        mockCourse.setCourseName("Java Basics");

        when(courseService.getCoursesByCategory("Programming")).thenReturn(Collections.singletonList(mockCourse));

        // Call the method
        ResponseEntity<?> response = searchController.getCoursesByCategory("Programming");
        List<CourseTO> courses = (List<CourseTO>) response.getBody();

        // Verify
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("Java Basics", courses.get(0).getCourseName());

        verify(courseService, times(1)).getCoursesByCategory("Programming");
    }

    @Test
    void testGetStudentCount() {
        // Mock data
        when(userService.getStudentCount()).thenReturn(42L);

        // Call the method
        ResponseEntity<?> response = searchController.getStudentCount();
        Long count = (Long) response.getBody();

        // Verify
        assertNotNull(count);
        assertEquals(42L, count);

        verify(userService, times(1)).getStudentCount();
    }

    @Test
    void testGetTutorsCount() {
        // Mock data
        when(userService.getTutorCount()).thenReturn(15L);

        // Call the method
        ResponseEntity<?> response = searchController.getTutorsCount();
        Long count = (Long) response.getBody();

        // Verify
        assertNotNull(count);
        assertEquals(15L, count);

        verify(userService, times(1)).getTutorCount();
    }

    @Test
    void testGetCoursesCount() {
        // Mock data
        when(courseService.getTotalCountOfCourses()).thenReturn(10L);

        // Call the method
        ResponseEntity<?> response = searchController.getCoursesCount();
        Long count = (Long) response.getBody();

        // Verify
        assertNotNull(count);
        assertEquals(10L, count);

        verify(courseService, times(1)).getTotalCountOfCourses();
    }
}
