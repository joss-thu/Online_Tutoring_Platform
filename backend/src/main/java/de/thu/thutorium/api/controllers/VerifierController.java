package de.thu.thutorium.api.controllers;

import de.thu.thutorium.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * VerifierController is a REST controller that handles verifier-related requests.
 */
@RestController
@RequestMapping("/verifier")
@RequiredArgsConstructor
@Validated
@Slf4j
public class VerifierController {

    private final UserService userService;

    /**
     * Verifies a tutor by their ID.
     *
     * <p>This endpoint verifies a tutor by their ID. If the tutor is successfully verified, a success message is returned.
     * If the tutor is not found, a {@link UsernameNotFoundException} is thrown and a 404 status is returned. If an unexpected
     * error occurs, a 500 status is returned with an error message.
     *
     * @param tutorId the ID of the tutor to be verified
     * @return a {@code ResponseEntity} containing a success message or an error message with the appropriate HTTP status
     */
    @Operation(
            summary = "Verify a tutor by their ID",
            description = "Verify a tutor entity by their ID. If the tutor is successfully verified, a success message is returned.",
            tags = {"Tutor Verification Endpoints"})
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tutor successfully verified.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tutor not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
    })
    @PutMapping("verify-tutor/{tutorId}")
    public ResponseEntity<?> verifyTutor(@PathVariable Long tutorId) {
        try {
            if (userService.verifyTutor(tutorId)) {
                return ResponseEntity.status(HttpStatus.OK).body("Tutor successfully verified.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tutor verification failed.");
            }
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + ex.getMessage());
        }
    }
}
