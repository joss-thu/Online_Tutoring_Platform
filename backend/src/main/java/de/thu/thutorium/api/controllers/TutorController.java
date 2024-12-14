package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.services.interfaces.CourseService;
import de.thu.thutorium.services.interfaces.MeetingService;
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
}
