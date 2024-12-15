package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.services.interfaces.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/** Controller class responsible for handling HTTP requests related to courses. */
@RestController
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  /**
   * Retrieves a course based on its ID.
   *
   * @param id The ID of the course to retrieve.
   * @return The {@link CourseTO} object representing the course.
   */
  @GetMapping("/course")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public CourseTO getCourseById(@RequestParam Long id) {
    return courseService.findCourseById(id);
  } // works

}
