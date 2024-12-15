package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import de.thu.thutorium.services.interfaces.ChatService;
import de.thu.thutorium.services.interfaces.UniversityService;
import de.thu.thutorium.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;  // Service to interact with the User repository
    private final UniversityService universityService;
    private final ChatService chatService;


    /**
     * Delete a user by their ID.
     * @param userId the ID of the user to delete
     * @return ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);  // Delegate the delete operation to the service
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/create-university")
    public ResponseEntity<UniversityDBO> createUniversity(
            @RequestBody @Valid UniversityTO university) {
        UniversityDBO created = universityService.createUniversity(university);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/chat-create")
    public ResponseEntity<String> createChat(@RequestBody @Valid ChatCreateTO requestDTO) {
        chatService.createChat(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Chat created successfully!");
    }

}
