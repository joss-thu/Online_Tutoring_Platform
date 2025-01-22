//package de.thu.thutorium.contollers;
//
//import de.thu.thutorium.api.controllers.AuthenticationController;
//import de.thu.thutorium.api.transferObjects.authentication.LogInRequestTO;
//import de.thu.thutorium.api.transferObjects.authentication.LogInResponseTO;
//import de.thu.thutorium.api.transferObjects.authentication.RegisterRequestTO;
//import de.thu.thutorium.exceptions.ResourceAlreadyExistsException;
//import de.thu.thutorium.services.interfaces.AuthenticationService;
//import de.thu.thutorium.database.dbObjects.enums.Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthenticationControllerTest {
//
//    @Mock
//    private AuthenticationService authenticationService;
//
//    @InjectMocks
//    private AuthenticationController authenticationController;
//
//    private RegisterRequestTO registerRequest;
//    private LogInRequestTO logInRequest;
//
//    @BeforeEach
//    void setUp() {
//        registerRequest = new RegisterRequestTO(
//                "test@example.com",
//                Role.TUTOR,
//                "John",
//                "Doe",
//                "password123"
//        );
//
//        logInRequest = LogInRequestTO.builder()
//                .email("test@example.com")
//                .password("password123")
//                .build();
//    }
//
//    @Test
//    void register_Success() {
//        LogInResponseTO responseBody = LogInResponseTO.builder().build();
//        when(authenticationService.register(registerRequest))
//                .thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));
//
//        ResponseEntity<?> response = authenticationController.register(registerRequest);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(responseBody, response.getBody());
//        verify(authenticationService, times(1)).register(registerRequest);
//    }
//
//    @Test
//    void register_Conflict() {
//        when(authenticationService.register(registerRequest))
//                .thenThrow(new ResourceAlreadyExistsException("User already exists"));
//
//        ResponseEntity<?> response = authenticationController.register(registerRequest);
//
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals("Error: User already exists", response.getBody());
//        verify(authenticationService, times(1)).register(registerRequest);
//    }
//
//    @Test
//    void register_InternalServerError() {
//        when(authenticationService.register(registerRequest))
//                .thenThrow(new RuntimeException("Unexpected error"));
//
//        ResponseEntity<?> response = authenticationController.register(registerRequest);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals("Unexpected Error: Unexpected error", response.getBody());
//        verify(authenticationService, times(1)).register(registerRequest);
//    }
//
//    @Test
//    void authenticate_Success() {
//        LogInResponseTO responseBody = LogInResponseTO.builder().build();
//        when(authenticationService.authenticate(logInRequest))
//                .thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));
//
//        ResponseEntity<?> response = authenticationController.authenticate(logInRequest);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(responseBody, response.getBody());
//        verify(authenticationService, times(1)).authenticate(logInRequest);
//    }
//
//    @Test
//    void authenticate_InternalServerError() {
//        when(authenticationService.authenticate(logInRequest))
//                .thenThrow(new RuntimeException("Unexpected error"));
//
//        ResponseEntity<?> response = authenticationController.authenticate(logInRequest);
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals("Unexpected Error: Unexpected error", response.getBody());
//        verify(authenticationService, times(1)).authenticate(logInRequest);
//    }
//}
