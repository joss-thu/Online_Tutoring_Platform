package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.CourseDTO;
import java.util.List;

public interface CourseService {
    CourseDTO findCourseById(Long id);
    List<CourseDTO> findCoursesByTutorName(String firstName, String lastName);
    List<CourseDTO> findCoursesByFullTutorName(String tutorName);
    List<CourseDTO> findCoursesByName(String name);
    List<CourseDTO> getCoursesByCategory(String categoryName);
    Long getTotalCountOfCourses();
}