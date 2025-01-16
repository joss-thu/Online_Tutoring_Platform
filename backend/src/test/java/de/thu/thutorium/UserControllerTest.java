package de.thu.thutorium;

import de.thu.thutorium.api.controllers.UserController;
import de.thu.thutorium.api.transferObjects.chat.ChatSummaryTO;
import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.api.transferObjects.common.MessageTO;
import de.thu.thutorium.api.transferObjects.common.UserTO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.services.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private MeetingService meetingService;

    @Mock
    private ChatService chatService;

    @Mock
    private MessageService messageService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private UserController userController;

    private UserTO userTO;

    @BeforeEach
    void setUp() {
        userTO = new UserTO();
        userTO.setUserId(1L);
        userTO.setFirstName("John");
        userTO.setLastName("Doe");
        userTO.setEmail("john.doe@example.com");
    }

    @Test
    void getUser_UserExists() {
        when(userService.findByUserId(1L)).thenReturn(userTO);

        ResponseEntity<?> response = userController.getUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userTO, response.getBody());
        verify(userService, times(1)).findByUserId(1L);
    }

    @Test
    void getUser_UserNotFound() {
        when(userService.findByUserId(1L)).thenThrow(new UsernameNotFoundException("User not found"));

        ResponseEntity<?> response = userController.getUser(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
        verify(userService, times(1)).findByUserId(1L);
    }

    @Test
    void updateUser_Success() {
        when(userService.updateUser(eq(1L), any(UserTO.class))).thenReturn(userTO);

        ResponseEntity<?> response = userController.updateUser(1L, userTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userTO, response.getBody());
        verify(userService, times(1)).updateUser(eq(1L), any(UserTO.class));
    }

    @Test
    void deleteMyAccount_Success() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDBO mockUser = mock(UserDBO.class);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(mockUser.getUserId()).thenReturn(1L);

        ResponseEntity<String> response = userController.deleteMyAccount();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User account with ID 1 has been successfully deleted.", response.getBody());
        verify(userService, times(1)).deleteUser(1L);
    }


    @Test
    void getMeetingsForUser_Success() {
        MeetingTO meeting = new MeetingTO();
        List<MeetingTO> meetings = List.of(meeting);
        when(meetingService.getMeetingsForUser(1L)).thenReturn(meetings);

        ResponseEntity<List<MeetingTO>> response = userController.getMeetingsForUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(meetings, response.getBody());
        verify(meetingService, times(1)).getMeetingsForUser(1L);
    }

    @Test
    void getChatSummaries_Success() {
        ChatSummaryTO chatSummary = new ChatSummaryTO();
        List<ChatSummaryTO> summaries = List.of(chatSummary);
        when(chatService.getChatSummaries(1L)).thenReturn(summaries);

        ResponseEntity<List<ChatSummaryTO>> response = userController.getChatSummaries(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(summaries, response.getBody());
        verify(chatService, times(1)).getChatSummaries(1L);
    }

    @Test
    void getChatMessages_Success() {
        MessageTO message = new MessageTO();
        List<MessageTO> messages = List.of(message);
        when(messageService.getMessagesByChatId(1L)).thenReturn(messages);

        ResponseEntity<List<MessageTO>> response = userController.getChatMessages(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
        verify(messageService, times(1)).getMessagesByChatId(1L);
    }

    @Test
    void getCoursesByTutor_Success() {
        CourseTO course = new CourseTO();
        List<CourseTO> courses = List.of(course);
        when(courseService.getCourseByTutorId(1L)).thenReturn(courses);

        ResponseEntity<List<CourseTO>> response = userController.getCoursesByTutor(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService, times(1)).getCourseByTutorId(1L);
    }
}
