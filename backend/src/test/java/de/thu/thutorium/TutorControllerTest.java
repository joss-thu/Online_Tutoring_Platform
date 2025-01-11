package de.thu.thutorium;

import de.thu.thutorium.api.controllers.TutorController;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.api.transferObjects.common.ProgressTO;
import de.thu.thutorium.services.interfaces.CourseService;
import de.thu.thutorium.services.interfaces.MeetingService;
import de.thu.thutorium.services.interfaces.ProgressService;
import de.thu.thutorium.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TutorControllerTest {

    @InjectMocks
    private TutorController tutorController;

    @Mock
    private MeetingService meetingService;

    @Mock
    private CourseService courseService;

    @Mock
    private ProgressService progressService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMeeting() {
        MeetingTO meetingTO = new MeetingTO();  // Assume MeetingTO has necessary properties
        doNothing().when(meetingService).createMeeting(meetingTO);

        ResponseEntity<String> response = tutorController.createMeeting(meetingTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Meeting created successfully", response.getBody());
        verify(meetingService, times(1)).createMeeting(meetingTO);
    }

    @Test
    void testDeleteMeeting() {
        Long meetingId = 1L;
        doNothing().when(meetingService).deleteMeeting(meetingId);

        ResponseEntity<String> response = tutorController.deleteMeeting(meetingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Meeting deleted successfully", response.getBody());
        verify(meetingService, times(1)).deleteMeeting(meetingId);
    }

    @Test
    void testUpdateMeeting() {
        Long meetingId = 1L;
        MeetingTO meetingTO = new MeetingTO(); // Assume updated details
        doNothing().when(meetingService).updateMeeting(meetingId, meetingTO);

        ResponseEntity<String> response = tutorController.updateMeeting(meetingId, meetingTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Meeting updated successfully", response.getBody());
        verify(meetingService, times(1)).updateMeeting(meetingId, meetingTO);
    }

    @Test
    void testCreateCourse() {
        CourseTO courseTO = new CourseTO(); // Assume CourseTO has necessary properties
        doNothing().when(courseService).createCourse(courseTO);

        ResponseEntity<String> response = tutorController.createCourse(courseTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course created successfully", response.getBody());
        verify(courseService, times(1)).createCourse(courseTO);
    }

    @Test
    void testDeleteCourse() {
        Long courseId = 1L;
        doNothing().when(courseService).deleteCourse(courseId);

        ResponseEntity<String> response = tutorController.deleteCourse(courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course deleted successfully", response.getBody());
        verify(courseService, times(1)).deleteCourse(courseId);
    }

    @Test
    void testUpdateCourse() {
        Long courseId = 1L;
        CourseTO courseTO = new CourseTO(); // Assume updated course details
        doNothing().when(courseService).updateCourse(courseId, courseTO);

        ResponseEntity<String> response = tutorController.updateCourse(courseId, courseTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course updated successfully", response.getBody());
        verify(courseService, times(1)).updateCourse(courseId, courseTO);
    }

    @Test
    void testCreateProgress() {
        ProgressTO progressTO = new ProgressTO(); // Assume ProgressTO has necessary properties
        doNothing().when(progressService).createProgress(progressTO);

        ResponseEntity<String> response = tutorController.createProgress(progressTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Progress created successfully", response.getBody());
        verify(progressService, times(1)).createProgress(progressTO);
    }

    @Test
    void testDeleteProgress() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(progressService.deleteProgress(studentId, courseId)).thenReturn(true);

        ResponseEntity<String> response = tutorController.deleteProgress(studentId, courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Progress deleted successfully", response.getBody());
        verify(progressService, times(1)).deleteProgress(studentId, courseId);
    }

    @Test
    void testDeleteProgressNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;
        when(progressService.deleteProgress(studentId, courseId)).thenReturn(false);

        ResponseEntity<String> response = tutorController.deleteProgress(studentId, courseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Progress record not found", response.getBody());
        verify(progressService, times(1)).deleteProgress(studentId, courseId);
    }

    @Test
    void testUpdateProgress() {
        Long studentId = 1L;
        Long courseId = 1L;
        Double points = 80.0;
        when(progressService.updateProgress(studentId, courseId, points)).thenReturn(true);

        ResponseEntity<String> response = tutorController.updateProgress(studentId, courseId, points);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Progress updated successfully", response.getBody());
        verify(progressService, times(1)).updateProgress(studentId, courseId, points);
    }

    @Test
    void testUpdateProgressNotFound() {
        Long studentId = 1L;
        Long courseId = 1L;
        Double points = 80.0;
        when(progressService.updateProgress(studentId, courseId, points)).thenReturn(false);

        ResponseEntity<String> response = tutorController.updateProgress(studentId, courseId, points);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Progress record not found", response.getBody());
        verify(progressService, times(1)).updateProgress(studentId, courseId, points);
    }
}
