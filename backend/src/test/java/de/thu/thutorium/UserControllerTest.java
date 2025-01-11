package de.thu.thutorium;

import de.thu.thutorium.api.controllers.UserController;
import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.api.transferObjects.common.UserTO;
import de.thu.thutorium.services.interfaces.MeetingService;
import de.thu.thutorium.services.interfaces.UserService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private MeetingService meetingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserSuccess() {
        Long userId = 1L;
        UserTO mockUser = new UserTO(userId, "John", "Doe", "john.doe@example.com", null, "Test user");

        when(userService.findByUserId(userId)).thenReturn(mockUser);

        ResponseEntity<?> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).findByUserId(userId);
    }

    @Test
    void testGetUserNotFound() {
        Long userId = 1L;

        when(userService.findByUserId(userId)).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<?> response = userController.getUser(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: User not found", response.getBody());
        verify(userService, times(1)).findByUserId(userId);
    }

    @Test
    void testUpdateUserSuccess() {
        Long userId = 1L;
        UserTO userToUpdate = new UserTO(null, "Updated", "Name", "updated.email@example.com", null, "Updated description");

        UserTO updatedUser = new UserTO(userId, "Updated", "Name", "updated.email@example.com", null, "Updated description");

        when(userService.updateUser(userId, userToUpdate)).thenReturn(updatedUser);

        ResponseEntity<?> response = userController.updateUser(userId, userToUpdate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).updateUser(userId, userToUpdate);
    }

    @Test
    void testUpdateUserValidationError() {
        Long userId = 1L;
        UserTO invalidUser = new UserTO(null, "", "Name", "invalidemail", null, null); // Invalid fields

        when(userService.updateUser(eq(userId), any(UserTO.class))).thenThrow(new ConstraintViolationException("Validation error", null));

        ResponseEntity<?> response = userController.updateUser(userId, invalidUser);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: Validation error", response.getBody());
        verify(userService, times(1)).updateUser(eq(userId), any(UserTO.class));
    }

    @Test
    void testDeleteMyAccountUnauthorized() {
        doThrow(new AuthenticationServiceException("User not authenticated")).when(userService).deleteUser(anyLong());

        ResponseEntity<String> response = userController.deleteMyAccount();

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User is not authenticated.", response.getBody());
    }

    @Test
    void testGetTutorByIdSuccess() {
        Long tutorId = 2L;
        UserTO mockTutor = new UserTO(tutorId, "Jane", "Tutor", "jane.tutor@example.com", null, "Experienced tutor");

        when(userService.getTutorByID(tutorId)).thenReturn(mockTutor);

        UserTO response = userController.getTutor(tutorId);

        assertEquals(mockTutor, response);
        verify(userService, times(1)).getTutorByID(tutorId);
    }

    @Test
    void testGetMeetingsForUserSuccess() {
        Long userId = 3L;
        List<MeetingTO> meetings = new ArrayList<>();
        meetings.add(new MeetingTO());
        meetings.add(new MeetingTO());

        when(meetingService.getMeetingsForUser(userId)).thenReturn(meetings);

        ResponseEntity<List<MeetingTO>> response = userController.getMeetingsForUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(meetings, response.getBody());
        verify(meetingService, times(1)).getMeetingsForUser(userId);
    }
}
