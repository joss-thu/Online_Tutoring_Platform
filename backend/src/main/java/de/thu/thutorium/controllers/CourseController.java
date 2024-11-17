package de.thu.thutorium.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thu.thutorium.models.Course;
import de.thu.thutorium.services.CourseService;

@RestController
@RequestMapping(path="api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public List<Course> getStudents(){
        return courseService.getCourses();
    }

    @PostMapping(path="add")
    public void registerCourse(@RequestBody Course course){
        courseService.addNewCourse(course);
    }

}
