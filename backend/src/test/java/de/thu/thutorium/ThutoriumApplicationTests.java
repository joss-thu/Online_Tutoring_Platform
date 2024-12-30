package de.thu.thutorium;

import de.thu.thutorium.api.controllers.AdminController;
import de.thu.thutorium.api.controllers.AuthenticationController;
import de.thu.thutorium.api.transferObjects.common.AddressTO;
import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.services.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ThutoriumApplicationTests {

    @Mock
    private UniversityService universityService;

    @Mock
    private AddressService addressService;

    @Mock
    private UserService userService;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testCreateUniversity() {
        // Given
        UniversityTO universityTO = new UniversityTO(); // Create a mock UniversityTO object
        when(universityService.createUniversity(universityTO)).thenReturn(universityTO);

        // When
        ResponseEntity<UniversityTO> response = adminController.createUniversity(universityTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(universityTO, response.getBody());
        verify(universityService, times(1)).createUniversity(universityTO); // Verify that the service was called once
    }

    @Test
    public void testCreateAddress() {
        // Given
        AddressTO addressTO = new AddressTO(); // Create a mock AddressTO object
        when(addressService.createAddress(addressTO)).thenReturn(addressTO);

        // When
        ResponseEntity<AddressTO> response = adminController.createAddress(addressTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(addressTO, response.getBody());
        verify(addressService, times(1)).createAddress(addressTO); // Verify that the service was called once
    }

    @Test
    public void testDeleteUser() {
        // Given
        Long userId = 1L; // Define a mock user ID

        // When
        ResponseEntity<String> response = adminController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
        verify(userService, times(1)).deleteUser(userId); // Verify that the service was called once
    }

    @Test
    public void testCreateChat() {
        // Given
        ChatCreateTO chatCreateTO = new ChatCreateTO(); // Create a mock ChatCreateTO object
        doNothing().when(chatService).createChat(chatCreateTO); // Mock the service method

        // When
        ResponseEntity<String> response = adminController.createChat(chatCreateTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Chat created successfully!", response.getBody());
        verify(chatService, times(1)).createChat(chatCreateTO); // Verify that the service was called once
    }

    @Test
    public void testDeleteChat() {
        // Given
        Long chatId = 1L; // Define a mock chat ID

        // When
        ResponseEntity<String> response = adminController.deleteChat(chatId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Chat deleted successfully!", response.getBody());
        verify(chatService, times(1)).deleteChat(chatId); // Verify that the service was called once
    }
}
