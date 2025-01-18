package de.thu.thutorium.services;

import de.thu.thutorium.api.TOMappers.CourseTOMapper;
import de.thu.thutorium.api.TOMappers.TutorTOMapper;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.TutorTO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.RatingCourseDBO;
import de.thu.thutorium.database.dbObjects.RatingTutorDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {

    @Mock
    private CourseTOMapper courseTOMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TutorTOMapper tutorTOMapper;

    @InjectMocks
    private SearchServiceImpl searchService;

    private UserDBO tutorDBO;
    private TutorTO tutorTO;
    private CourseDBO courseDBO;
    private CourseTO courseTO;

    @BeforeEach
    void setUp() {
        tutorDBO = UserDBO.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        tutorTO = new TutorTO();
        tutorTO.setUserId(1L);
        tutorTO.setFullName("John Doe");
        tutorTO.setAverageRating(4.5);

        courseDBO = CourseDBO.builder()
                .courseId(1L)
                .courseName("Sample Course")
                .build();

        courseTO = new CourseTO();
        courseTO.setCourseId(1L);
        courseTO.setCourseName("Sample Course");
        courseTO.setAverageRating(4.0);
    }

    @Test
    void searchTutors_ShouldReturnTutorList_WhenMatchingTutorsExist() {
        when(userRepository.findByTutorFullName("John Doe")).thenReturn(List.of(tutorDBO));
        when(tutorTOMapper.toDTO(tutorDBO)).thenReturn(tutorTO);

        List<TutorTO> result = searchService.searchTutors("John Doe");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        verify(userRepository, times(1)).findByTutorFullName("John Doe");
        verify(tutorTOMapper, times(1)).toDTO(tutorDBO);
    }

    @Test
    void searchTutors_ShouldReturnEmptyList_WhenNoMatchingTutorsExist() {
        when(userRepository.findByTutorFullName("Unknown"))
                .thenReturn(List.of());

        List<TutorTO> result = searchService.searchTutors("Unknown");

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByTutorFullName("Unknown");
        verify(tutorTOMapper, never()).toDTO(any());
    }

    @Test
    void mapWithAverageTutorRating_ShouldReturnTutorWithAverageRating() throws Exception {
        RatingTutorDBO rating1 = new RatingTutorDBO();
        rating1.setPoints(5.0);
        RatingTutorDBO rating2 = new RatingTutorDBO();
        rating2.setPoints(4.0);

        UserDBO tutorDBO = new UserDBO();
        tutorDBO.setReceivedTutorRatings(List.of(rating1, rating2));

        TutorTO expectedTutorTO = new TutorTO();
        expectedTutorTO.setAverageRating(4.5);

        when(tutorTOMapper.toDTO(tutorDBO)).thenReturn(expectedTutorTO);

        Method method = SearchServiceImpl.class.getDeclaredMethod("mapWithAverageTutorRating", UserDBO.class);
        method.setAccessible(true);
        TutorTO result = (TutorTO) method.invoke(searchService, tutorDBO);

        assertEquals(4.5, result.getAverageRating());
    }

    @Test
    void mapWithAverageRating_ShouldReturnCourseWithAverageRating() throws Exception {
        RatingCourseDBO rating1 = new RatingCourseDBO();
        rating1.setPoints(5.0);
        RatingCourseDBO rating2 = new RatingCourseDBO();
        rating2.setPoints(3.0);

        CourseDBO courseDBO = new CourseDBO();
        courseDBO.setReceivedCourseRatings(List.of(rating1, rating2));

        CourseTO expectedCourseTO = new CourseTO();
        expectedCourseTO.setAverageRating(4.0);

        when(courseTOMapper.toDTO(courseDBO)).thenReturn(expectedCourseTO);

        Method method = SearchServiceImpl.class.getDeclaredMethod("mapWithAverageRating", CourseDBO.class);
        method.setAccessible(true); // Bypass private visibility
        CourseTO result = (CourseTO) method.invoke(searchService, courseDBO);

        assertEquals(4.0, result.getAverageRating());
    }

}
