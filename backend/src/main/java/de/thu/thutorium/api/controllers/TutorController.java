package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.api.transferObjects.common.ProgressTO;
import de.thu.thutorium.api.transferObjects.common.UserTO;
import de.thu.thutorium.services.interfaces.CourseService;
import de.thu.thutorium.services.interfaces.MeetingService;
import de.thu.thutorium.services.interfaces.ProgressService;
import de.thu.thutorium.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController {
    private final MeetingService meetingService;
    private final CourseService courseService;
    private final UserService userService;
    private final ProgressService progressService;


    //Create Meeting
    @PostMapping("/create-meeting")
    public ResponseEntity<String> createMeeting(@RequestBody @Valid MeetingTO meetingTO) {
        meetingService.createMeeting(meetingTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Meeting created successfully");
    }

    //Delete Meeting
    @DeleteMapping("/delete-meeting/{meetingId}")
    public ResponseEntity<String> deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok("Meeting deleted successfully");
    }

    //Update Meeting
    @PutMapping("/update-meeting/{meetingId}")
    public ResponseEntity<String> updateMeeting(
            @PathVariable Long meetingId,
            @RequestBody @Valid MeetingTO meetingTO) {
        meetingService.updateMeeting(meetingId, meetingTO);
        return ResponseEntity.ok("Meeting updated successfully");
    }

    /** Course Operations */
    //Create Course
    @PostMapping("/course/create")
    public ResponseEntity<String> createCourse(@RequestBody CourseTO courseTO) {
        courseService.createCourse(courseTO);
        return ResponseEntity.ok("Course created successfully"); // Return HTTP 201 status
    }

    //Delete Course
    @DeleteMapping("/delete-course/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }

    //Update Meeting
    @PutMapping("/update-course/{courseId}")
    public ResponseEntity<String> updateCourse(
            @PathVariable Long courseId,
            @RequestBody @Valid CourseTO courseTO) {
        courseService.updateCourse(courseId, courseTO);
        return ResponseEntity.ok("Course updated successfully");
    }


    //--------
    //Get tutor by ID
    @GetMapping("tutor")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public UserTO getTutor(@RequestParam Long id) {
        return userService.getTutorByID(id);
    }


    //---------- Progress ----------

    @PostMapping("/create-progress")
    public ResponseEntity<String> createProgress(@Valid @RequestBody ProgressTO progressTO) {
        progressService.createProgress(progressTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Progress created successfully");
    }

    @DeleteMapping("/delete-progress/{studentId}/{courseId}")
    public ResponseEntity<String> deleteProgress(@PathVariable Long studentId, @PathVariable Long courseId) {
        boolean isDeleted = progressService.deleteProgress(studentId, courseId);
        if (isDeleted) {
            return ResponseEntity.ok("Progress deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress record not found");
        }
    }

    @PutMapping("/update-progress/{studentId}/{courseId}")
    public ResponseEntity<String> updateProgress(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestParam Double points) {

        boolean isUpdated = progressService.updateProgress(studentId, courseId, points);
        if (isUpdated) {
            return ResponseEntity.ok("Progress updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress record not found");
        }
    }
}
