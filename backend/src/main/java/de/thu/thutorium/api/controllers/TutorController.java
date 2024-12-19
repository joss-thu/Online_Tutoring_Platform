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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * TutorController provides REST API endpoints for managing tutor-related operations such as
 * creating and managing meetings, courses, and progress records. It also includes functionality for
 * retrieving tutor information and deleting user accounts.
 *
 * <p>This controller handles requests under the "/tutor" path.
 *
 * <p>Annotations used:
 *
 * <ul>
 *   <li>@RestController: Marks this class as a REST controller.
 *   <li>@RequestMapping("/tutor"): Maps all requests starting with "/tutor".
 *   <li>@RequiredArgsConstructor: Generates a constructor for final fields.
 *   <li>@CrossOrigin: Enables cross-origin requests for specified endpoints.
 * </ul>
 */
@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController {
  /** Service for managing meeting-related operations. */
  private final MeetingService meetingService;

  /** Service for managing course-related operations. */
  private final CourseService courseService;

  /** Service for managing user-related operations. */
  private final UserService userService;

  /** Service for managing progress-related operations. */
  private final ProgressService progressService;

  /**
   * Creates a new meeting.
   *
   * @param meetingTO the {@link MeetingTO} object containing meeting details.
   * @return a success message.
   */
  @PostMapping("/create-meeting")
  public ResponseEntity<String> createMeeting(@RequestBody @Valid MeetingTO meetingTO) {
    meetingService.createMeeting(meetingTO);
    return ResponseEntity.status(HttpStatus.CREATED).body("Meeting created successfully");
  }

  /**
   * Deletes a meeting by its ID.
   *
   * @param meetingId the ID of the meeting to delete.
   * @return a success message.
   */
  @DeleteMapping("/delete-meeting/{meetingId}")
  public ResponseEntity<String> deleteMeeting(@PathVariable Long meetingId) {
    meetingService.deleteMeeting(meetingId);
    return ResponseEntity.ok("Meeting deleted successfully");
  }

  /**
   * Updates a meeting by its ID.
   *
   * @param meetingId the ID of the meeting to update.
   * @param meetingTO the {@link MeetingTO} object containing updated meeting details.
   * @return a success message.
   */
  @PutMapping("/update-meeting/{meetingId}")
  public ResponseEntity<String> updateMeeting(
      @PathVariable Long meetingId, @RequestBody @Valid MeetingTO meetingTO) {
    meetingService.updateMeeting(meetingId, meetingTO);
    return ResponseEntity.ok("Meeting updated successfully");
  }

  /** Course Operations */

  /**
   * Creates a new course.
   *
   * @param courseTO the {@link CourseTO} object containing course details.
   * @return a success message.
   */
  @PostMapping("/course/create")
  public ResponseEntity<String> createCourse(@RequestBody CourseTO courseTO) {
    courseService.createCourse(courseTO);
    return ResponseEntity.ok("Course created successfully"); // Return HTTP 201 status
  }

  /**
   * Deletes a course by its ID.
   *
   * @param courseId the ID of the course to delete.
   * @return a success message.
   */
  @DeleteMapping("/delete-course/{courseId}")
  public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
    courseService.deleteCourse(courseId);
    return ResponseEntity.ok("Course deleted successfully");
  }

  /**
   * Updates a course by its ID.
   *
   * @param courseId the ID of the course to update.
   * @param courseTO the {@link CourseTO} object containing updated course details.
   * @return a success message.
   */
  @PutMapping("/update-course/{courseId}")
  public ResponseEntity<String> updateCourse(
      @PathVariable Long courseId, @RequestBody @Valid CourseTO courseTO) {
    courseService.updateCourse(courseId, courseTO);
    return ResponseEntity.ok("Course updated successfully");
  }

  /** User Operations */

  /**
   * Retrieves a tutor by their ID.
   *
   * @param id the ID of the tutor to retrieve.
   * @return the {@link UserTO} object containing tutor details.
   */
  @GetMapping("tutor")
  @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
  public UserTO getTutor(@RequestParam Long id) {
    return userService.getTutorByID(id);
  }

  /** Progress Operations */

  /**
   * Creates a new progress record.
   *
   * @param progressTO the {@link ProgressTO} object containing progress details.
   * @return a success message.
   */
  @PostMapping("/create-progress")
  public ResponseEntity<String> createProgress(@Valid @RequestBody ProgressTO progressTO) {
    progressService.createProgress(progressTO);
    return ResponseEntity.status(HttpStatus.CREATED).body("Progress created successfully");
  }

  /**
   * Deletes a progress record by student ID and course ID.
   *
   * @param studentId the ID of the student.
   * @param courseId the ID of the course.
   * @return a success message or a not-found message.
   */
  @DeleteMapping("/delete-progress/{studentId}/{courseId}")
  public ResponseEntity<String> deleteProgress(
      @PathVariable Long studentId, @PathVariable Long courseId) {
    boolean isDeleted = progressService.deleteProgress(studentId, courseId);
    if (isDeleted) {
      return ResponseEntity.ok("Progress deleted successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress record not found");
    }
  }

  /**
   * Updates a progress record by student ID and course ID.
   *
   * @param studentId the ID of the student.
   * @param courseId the ID of the course.
   * @param points the new progress points.
   * @return a success message or a not-found message.
   */
  @PutMapping("/update-progress/{studentId}/{courseId}")
  public ResponseEntity<String> updateProgress(
      @PathVariable Long studentId, @PathVariable Long courseId, @RequestParam Double points) {

    boolean isUpdated = progressService.updateProgress(studentId, courseId, points);
    if (isUpdated) {
      return ResponseEntity.ok("Progress updated successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress record not found");
    }
  }

  /**
   * Deletes the currently authenticated user's account.
   *
   * @return a success message.
   */
  @DeleteMapping("/delete-my-account")
  public ResponseEntity<String> deleteMyAccount() {
    // Retrieve the currently authenticated user's ID
    Long authenticatedUserId = getAuthenticatedUserId();
    userService.deleteUser(authenticatedUserId);
    return ResponseEntity.ok("Your account has been deleted successfully");
  }

  /**
   * Retrieves the authenticated user's ID.
   *
   * @return the ID of the authenticated user.
   * @throws SecurityException if the user is not authenticated.
   */
  private Long getAuthenticatedUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new SecurityException("User is not authenticated");
    }
    // Assuming the user's ID is stored as the principal or can be derived from it
    return (Long) authentication.getPrincipal(); // Adjust based on your authentication setup
  }
}
