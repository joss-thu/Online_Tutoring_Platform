package de.thu.thutorium.services.implementations;

import de.thu.thutorium.database.dbObjects.Course;
import de.thu.thutorium.database.exceptions.ResourceNotFoundException;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.api.transferObjects.CourseDTO;
import de.thu.thutorium.api.frontendMappers.CourseMapper;
import de.thu.thutorium.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** Service class responsible for handling operations related to courses. */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public CourseDTO findCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
        return courseMapper.toDTO(course);
    }

    @Override
    public List<CourseDTO> findCoursesByTutorName(String firstName, String lastName) {
        List<Course> courses = courseRepository.findByTutorFirstNameAndLastName(firstName, lastName);
        return courseMapper.toDTOList(courses);
    }

    @Override
    public List<CourseDTO> findCoursesByFullTutorName(String tutorName) {
        List<Course> courses = courseRepository.findByTutorFullName(tutorName);
        return courseMapper.toDTOList(courses);
    }

    @Override
    public List<CourseDTO> findCoursesByName(String name) {
        List<Course> courses = courseRepository.findCourseByName(name);
        return courseMapper.toDTOList(courses);
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(String categoryName) {
        List<Course> courses = courseRepository.findCoursesByCategoryName(categoryName);
        return courses.stream()
                .map(courseMapper::toDTO)
                .toList();
    }

    @Override
    public Long getTotalCountOfCourses() {
        return courseRepository.countAllCourses();
    }
}

