package de.thu.thutorium.api.controllers;

import de.thu.thutorium.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;  // Service to interact with the User repository

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
}
