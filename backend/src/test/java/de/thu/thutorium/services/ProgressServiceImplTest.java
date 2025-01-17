package de.thu.thutorium.services;

import de.thu.thutorium.api.transferObjects.common.ProgressTO;
import de.thu.thutorium.database.DBOMappers.ProgressDBMapper;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.ProgressDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.ProgressRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.ProgressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressServiceImplTest {

    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ProgressDBMapper progressDBMapper;

    @InjectMocks
    private ProgressServiceImpl progressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProgress_Success() {
        ProgressTO progressTO = new ProgressTO();
        progressTO.setStudentId(1L);
        progressTO.setCourseId(2L);
        progressTO.setPoints(95.0);

        UserDBO mockUser = new UserDBO();
        mockUser.setFirstName("Alice");
        mockUser.setReceivedScores(new ArrayList<>());

        CourseDBO mockCourse = new CourseDBO();
        mockCourse.setCourseName("Test Course");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(mockCourse));

        ProgressDBO mockProgressDBO = new ProgressDBO();
        mockProgressDBO.setPoints(95.0);
        mockProgressDBO.setStudent(mockUser);
        mockProgressDBO.setCourse(mockCourse);

        when(progressDBMapper.toEntity(progressTO, mockUser, mockCourse))
                .thenReturn(mockProgressDBO);

        progressService.createProgress(progressTO);

        assertEquals(1, mockUser.getReceivedScores().size(),
                "User should have 1 progress record after creation");
        verify(userRepository).findById(1L);
        verify(courseRepository).findById(2L);
        verify(progressDBMapper).toEntity(progressTO, mockUser, mockCourse);
        verify(userRepository).save(mockUser);
        verify(progressRepository).save(mockProgressDBO);
    }

    @Test
    void testCreateProgress_InvalidUser() {
        ProgressTO progressTO = new ProgressTO();
        progressTO.setStudentId(999L);
        progressTO.setCourseId(2L);

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> progressService.createProgress(progressTO),
                "Expected exception for invalid student ID"
        );
        assertTrue(thrown.getMessage().contains("Invalid student ID: 999"));
        verify(userRepository).findById(999L);
        verify(courseRepository, never()).findById(anyLong());
        verify(progressDBMapper, never()).toEntity(any(), any(), any());
    }

    @Test
    void testCreateProgress_InvalidCourse() {
        ProgressTO progressTO = new ProgressTO();
        progressTO.setStudentId(1L);
        progressTO.setCourseId(999L);

        UserDBO mockUser = new UserDBO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> progressService.createProgress(progressTO),
                "Expected exception for invalid course ID"
        );
        assertTrue(thrown.getMessage().contains("Invalid course ID: 999"));
        verify(userRepository).findById(1L);
        verify(courseRepository).findById(999L);
        verify(progressDBMapper, never()).toEntity(any(), any(), any());
    }

    @Test
    void testDeleteProgress_RecordFound() {
        Long userId = 1L;
        Long courseId = 2L;

        ProgressDBO mockProgress = new ProgressDBO();
        mockProgress.setStudent(new UserDBO());
        mockProgress.setCourse(new CourseDBO());
        when(progressRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(mockProgress);

        boolean result = progressService.deleteProgress(userId, courseId);

        assertTrue(result);
        verify(progressRepository).findByUserIdAndCourseId(userId, courseId);
        verify(progressRepository).delete(mockProgress);
    }

    @Test
    void testDeleteProgress_RecordNotFound() {
        Long userId = 1L;
        Long courseId = 999L;

        when(progressRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(null);

        boolean result = progressService.deleteProgress(userId, courseId);

        assertFalse(result);
        verify(progressRepository).findByUserIdAndCourseId(userId, courseId);
        verify(progressRepository, never()).delete(any(ProgressDBO.class));
    }

    @Test
    void testUpdateProgress_RecordFound() {
        Long userId = 1L;
        Long courseId = 2L;
        Double newPoints = 75.0;

        ProgressDBO mockProgress = new ProgressDBO();
        mockProgress.setPoints(50.0);
        when(progressRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(mockProgress);

        boolean result = progressService.updateProgress(userId, courseId, newPoints);

        assertTrue(result);
        assertEquals(75.0, mockProgress.getPoints());
        verify(progressRepository).findByUserIdAndCourseId(userId, courseId);
        verify(progressRepository).save(mockProgress);
    }

    @Test
    void testUpdateProgress_RecordNotFound() {
        Long userId = 1L;
        Long courseId = 999L;
        Double newPoints = 75.0;

        when(progressRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(null);

        boolean result = progressService.updateProgress(userId, courseId, newPoints);

        assertFalse(result);
        verify(progressRepository).findByUserIdAndCourseId(userId, courseId);
        verify(progressRepository, never()).save(any(ProgressDBO.class));
    }
}
