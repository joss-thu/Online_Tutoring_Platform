package de.thu.thutorium.services;


import de.thu.thutorium.api.TOMappers.CourseTOMapper;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.database.DBOMappers.CourseDBOMapper;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.CourseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseDBOMapper courseDBOMapper;

    @Mock
    private CourseTOMapper courseTOMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private UserDBO tutor;
    private CourseDBO courseDBO;
    private CourseTO courseTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Prepare mock objects
        tutor = new UserDBO();
        tutor.getUserId();
        tutor.setFirstName("John");
        tutor.setLastName("Doe");

        courseDBO = new CourseDBO();
        courseDBO.getCourseId();
        courseDBO.setCourseName("Test Course");
        courseDBO.setTutor(tutor);
        courseDBO.setCreatedOn(LocalDateTime.now());

        courseTO = new CourseTO();
        courseTO.setCourseId(1L);
        courseTO.setCourseName("Test Course");
        courseTO.setTutorId(1L);
    }

    @Test
    void testFindCourseById() {
        // Arrange
        when(courseRepository.findCourseDBOByCourseId(1L)).thenReturn(Optional.of(courseDBO));
        when(courseTOMapper.toDTO(courseDBO)).thenReturn(courseTO);

        // Act
        CourseTO result = courseService.findCourseById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Course", result.getCourseName());
        verify(courseRepository, times(1)).findCourseDBOByCourseId(1L);
    }

    @Test
    void testFindCourseById_NotFound() {
        // Arrange
        when(courseRepository.findCourseDBOByCourseId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> courseService.findCourseById(1L));
    }

    @Test
    void testCreateCourse() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(courseDBOMapper.toDBO(courseTO)).thenReturn(courseDBO);

        // Act
        courseService.createCourse(courseTO);

        // Assert
        verify(courseRepository, times(1)).save(courseDBO);
        assertEquals(tutor, courseDBO.getTutor());
        assertNotNull(courseDBO.getCreatedOn());
    }

    @Test
    void testUpdateCourse() {
        // Arrange
        CourseTO updatedCourseTO = new CourseTO();
        updatedCourseTO.setCourseName("Updated Course");
        updatedCourseTO.setTutorId(1L);

        // Mock behaviors
        when(courseRepository.findById(1L)).thenReturn(Optional.of(courseDBO));
        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(courseDBOMapper.toDBO(updatedCourseTO)).thenReturn(courseDBO);
        when(courseRepository.save(courseDBO)).thenReturn(courseDBO);
        when(courseTOMapper.toDTO(courseDBO)).thenReturn(courseTO);

        // Act
        CourseTO updatedCourse = courseService.updateCourse(1L, updatedCourseTO);

        // Assert
        assertNotNull(updatedCourse, "The updated course should not be null");
        assertEquals("Test Course", updatedCourse.getCourseName());
        verify(courseRepository, times(1)).save(courseDBO);
    }

    @Test
    void testUpdateCourse_CourseNotFound() {
        // Arrange
        CourseTO updatedCourseTO = new CourseTO();
        updatedCourseTO.setCourseName("Updated Course");
        updatedCourseTO.setTutorId(1L);


}
}
