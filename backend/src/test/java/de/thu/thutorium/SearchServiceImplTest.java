package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.CourseCategoryTOMapper;
import de.thu.thutorium.api.TOMappers.CourseTOMapper;
import de.thu.thutorium.api.TOMappers.TutorTOMapper;
import de.thu.thutorium.api.transferObjects.common.CourseCategoryTO;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.TutorTO;
import de.thu.thutorium.database.dbObjects.*;
import de.thu.thutorium.database.repositories.CategoryRepository;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseTOMapper courseTOMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TutorTOMapper tutorTOMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CourseCategoryTOMapper courseCategoryTOMapper;

    @InjectMocks
    private SearchServiceImpl searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchTutors_Found() {
        String tutorName = "John";
        UserDBO userDBO1 = new UserDBO();
        userDBO1.setFirstName("John");
        userDBO1.setLastName("Doe");

        RatingTutorDBO rating1 = new RatingTutorDBO();
        rating1.setPoints(4.0);
        RatingTutorDBO rating2 = new RatingTutorDBO();
        rating2.setPoints(5.0);
        userDBO1.setReceivedTutorRatings(Arrays.asList(rating1, rating2));

        when(userRepository.findByTutorFullName(tutorName))
                .thenReturn(List.of(userDBO1));

        TutorTO mappedTutor = new TutorTO();
        mappedTutor.setAverageRating(0.0); // We'll check final updated rating
        when(tutorTOMapper.toDTO(userDBO1)).thenReturn(mappedTutor);

        List<TutorTO> result = searchService.searchTutors(tutorName);

        assertNotNull(result);
        assertEquals(1, result.size());
        TutorTO tutor = result.get(0);
        assertEquals(4.5, tutor.getAverageRating(), 0.001);
        verify(userRepository, times(1)).findByTutorFullName(tutorName);
        verify(tutorTOMapper, times(1)).toDTO(userDBO1);
    }

    @Test
    void testSearchTutors_NotFound() {
        String tutorName = "NonExistent";
        when(userRepository.findByTutorFullName(tutorName))
                .thenReturn(Collections.emptyList());

        List<TutorTO> result = searchService.searchTutors(tutorName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByTutorFullName(tutorName);
        verify(tutorTOMapper, never()).toDTO(any(UserDBO.class));
    }

    @Test
    void testSearchCourses_Found() {
        String courseName = "Math";
        CourseDBO courseDBO = new CourseDBO();
        courseDBO.setCourseName("Mathematics");

        RatingCourseDBO rating1 = new RatingCourseDBO();
        rating1.setPoints(3.0);
        RatingCourseDBO rating2 = new RatingCourseDBO();
        rating2.setPoints(5.0);
        courseDBO.setReceivedCourseRatings(Arrays.asList(rating1, rating2));

        when(courseRepository.findCourseByName(courseName))
                .thenReturn(List.of(courseDBO));

        CourseTO mappedCourseTO = new CourseTO();
        mappedCourseTO.setAverageRating(0.0); // Will be recalculated to 4.0
        when(courseTOMapper.toDTO(courseDBO)).thenReturn(mappedCourseTO);

        List<CourseTO> result = searchService.searchCourses(courseName);

        assertNotNull(result);
        assertEquals(1, result.size());
        CourseTO course = result.get(0);
        assertEquals(4.0, course.getAverageRating(), 0.001);

        verify(courseRepository, times(1)).findCourseByName(courseName);
        verify(courseTOMapper, times(1)).toDTO(courseDBO);
    }

    @Test
    void testSearchCourses_EmptyResult() {
        String courseName = "NonExistentCourse";
        when(courseRepository.findCourseByName(courseName))
                .thenReturn(Collections.emptyList());

        List<CourseTO> result = searchService.searchCourses(courseName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseRepository, times(1)).findCourseByName(courseName);
        verify(courseTOMapper, never()).toDTO(any(CourseDBO.class));
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        CourseCategoryDBO category1 = new CourseCategoryDBO();
        category1.setCategoryName("Programming");
        CourseCategoryDBO category2 = new CourseCategoryDBO();
        category2.setCategoryName("Language");

        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(category1, category2));

        CourseCategoryTO categoryTO1 = new CourseCategoryTO();
        categoryTO1.setCategoryName("Programming");
        CourseCategoryTO categoryTO2 = new CourseCategoryTO();
        categoryTO2.setCategoryName("Language");

        when(courseCategoryTOMapper.toDTO(category1)).thenReturn(categoryTO1);
        when(courseCategoryTOMapper.toDTO(category2)).thenReturn(categoryTO2);

        List<CourseCategoryTO> result = searchService.getAllCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Programming", result.get(0).getCategoryName());
        assertEquals("Language", result.get(1).getCategoryName());
        verify(categoryRepository, times(1)).findAll();
        verify(courseCategoryTOMapper, times(1)).toDTO(category1);
        verify(courseCategoryTOMapper, times(1)).toDTO(category2);
    }
}
