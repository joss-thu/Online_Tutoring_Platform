package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.UserTOMapper;
import de.thu.thutorium.api.transferObjects.common.RatingTutorTO;
import de.thu.thutorium.api.transferObjects.common.UserTO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.RoleDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.dbObjects.enums.Role;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.RatingTutorRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RatingTutorRepository ratingTutorRepository;

    @Mock
    private UserTOMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDBO student;
    private UserDBO tutor;
    private CourseDBO course;

    @BeforeEach
    void setUp() {
        RoleDBO studentRole = new RoleDBO();
        setField(studentRole, "roleName", Role.STUDENT);

        RoleDBO tutorRole = new RoleDBO();
        setField(tutorRole, "roleName", Role.TUTOR);

        student = UserDBO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .roles(Collections.singleton(studentRole))
                .build();

        setField(student, "userId", 1L);

        tutor = UserDBO.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .roles(Collections.singleton(tutorRole))
                .build();

        setField(tutor, "userId", 2L);

        course = CourseDBO.builder()
                .courseName("Test Course")
                .tutor(tutor)
                .build();

        setField(course, "courseId", 1L);
    }

    @Test
    void getStudentCount_ShouldReturnCorrectCount() {
        when(userRepository.findAll()).thenReturn(List.of(student, tutor));

        Long studentCount = userService.getStudentCount();

        assertEquals(1, studentCount);
    }

    @Test
    void getTutorCount_ShouldReturnCorrectCount() {
        when(userRepository.findAll()).thenReturn(List.of(student, tutor));

        Long tutorCount = userService.getTutorCount();

        assertEquals(1, tutorCount);
    }

    @Test
    void findByUserId_ShouldReturnUserTO_WhenUserExists() {
        UserTO userTO = new UserTO();
        userTO.setUserId(1L);
        userTO.setFirstName("John");
        userTO.setLastName("Doe");

        when(userRepository.findUserDBOByUserId(1L)).thenReturn(Optional.of(student));
        when(userMapper.toDTO(student)).thenReturn(userTO);

        UserTO result = userService.findByUserId(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void findByUserId_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findUserDBOByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByUserId(1L));
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.findUserDBOByUserId(1L)).thenReturn(Optional.of(student));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(student);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findUserDBOByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void enrollCourse_ShouldEnrollStudent_WhenValid() {
        when(userRepository.findUserDBOByUserId(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findCourseDBOByCourseId(1L)).thenReturn(Optional.of(course));

        userService.enrollCourse(1L, 1L);

        assertTrue(student.getStudentCourses().contains(course));
        verify(userRepository, times(1)).save(student);
    }

    @Test
    void enrollCourse_ShouldThrowException_WhenStudentNotFound() {
        when(userRepository.findUserDBOByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.enrollCourse(1L, 1L));
    }

    @Test
    void enrollCourse_ShouldThrowException_WhenCourseNotFound() {
        when(userRepository.findUserDBOByUserId(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findCourseDBOByCourseId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.enrollCourse(1L, 1L));
    }

    @Test
    void rateTutor_ShouldSaveRating_WhenValid() {
        RatingTutorTO ratingTutorTO = new RatingTutorTO();
        ratingTutorTO.setStudentId(1L);
        ratingTutorTO.setTutorId(2L);
        ratingTutorTO.setPoints(5.0);
        ratingTutorTO.setReview("Great tutor");

        student.getStudentCourses().add(course);
        tutor.getTutorCourses().add(course);

        when(userRepository.findUserDBOByUserIdAndRoles_RoleName(1L, Role.STUDENT))
                .thenReturn(Optional.of(student));
        when(userRepository.findUserDBOByUserIdAndRoles_RoleName(2L, Role.TUTOR))
                .thenReturn(Optional.of(tutor));

        userService.rateTutor(ratingTutorTO);

        verify(ratingTutorRepository, times(1)).save(any());
    }


    @Test
    void unenrollCourse_ShouldUnenrollStudent_WhenValid() {
        student.getStudentCourses().add(course);
        course.getStudents().add(student);

        when(userRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        userService.unenrollCourse(1L, 1L);

        assertFalse(student.getStudentCourses().contains(course));
        assertFalse(course.getStudents().contains(student));
        verify(userRepository, times(1)).save(student);
        verify(courseRepository, times(1)).save(course);
    }

    /**
     * Helper method to set private fields using reflection.
     */
    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set field via reflection", e);
        }
    }
}
