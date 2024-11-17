package de.thu.thutorium.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thu.thutorium.models.Course;
import de.thu.thutorium.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public void addNewCourse(Course course) {
        courseRepository.save(course);
    }


}
