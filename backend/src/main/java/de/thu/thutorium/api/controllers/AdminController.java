package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.AddressTO;
import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import de.thu.thutorium.api.transferObjects.common.CourseCategoryTO;
import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.services.interfaces.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController provides REST API endpoints for managing administrative tasks such as creating
 * universities, addresses, and categories, as well as user and chat management.
 *
 * <p>This controller handles requests under the "/admin" path and uses services to perform the
 * necessary operations.
 *
 * <p>Annotations used:
 *
 * <ul>
 *   <li>@RestController: Marks this class as a REST controller.
 *   <li>@RequestMapping("/admin"): Maps all requests starting with "/admin".
 *   <li>@RequiredArgsConstructor: Generates a constructor for final fields.
 *   <li>@Validated: Enables validation on methods.
 * </ul>
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

  /** Service for managing university-related operations. */
  private final UniversityService universityService;

  /** Service for managing address-related operations. */
  private final AddressService addressService;

  /** Service for managing user-related operations. */
  private final UserService userService; // Service to interact with the User repository

  /** Service for managing chat-related operations. */
  private final ChatService chatService;

  /** Service for managing course category operations. */
  private final CategoryService categoryService;

  /**
   * Creates a new university.
   *
   * @param university the {@link UniversityTO} object containing university details.
   * @return the created {@link UniversityTO} object.
   */
  @PostMapping("create-university")
  public ResponseEntity<UniversityTO> createUniversity(
      @Valid @RequestBody UniversityTO university) {
    UniversityTO created = universityService.createUniversity(university);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  /**
   * Creates a new address.
   *
   * @param address the {@link AddressTO} object containing address details.
   * @return the created {@link AddressTO} object.
   */
  @PostMapping("create-address")
  public ResponseEntity<AddressTO> createAddress(@Valid @RequestBody AddressTO address) {
    AddressTO created = addressService.createAddress(address);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  /**
   * Deletes a user by their ID.
   *
   * @param userId the ID of the user to delete.
   * @return a success message.
   */
  @DeleteMapping("/delete-user/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId); // Delegate the delete operation to the service
    return ResponseEntity.ok("User deleted successfully");
  }

  /**
   * Creates a new chat.
   *
   * @param requestDTO the {@link ChatCreateTO} object containing chat details.
   * @return a success message.
   */
  @PostMapping("/chat-create")
  public ResponseEntity<String> createChat(@RequestBody @Valid ChatCreateTO requestDTO) {
    chatService.createChat(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body("Chat created successfully!");
  }

  /**
   * Deletes a chat by its ID.
   *
   * @param chatId the ID of the chat to delete.
   * @return a success message.
   */
  @DeleteMapping("/chat-delete/{chatId}")
  public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
    chatService.deleteChat(chatId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Chat deleted successfully!");
  }

  /**
   * Creates a new course category.
   *
   * @param courseCategoryTO the {@link CourseCategoryTO} object containing category details.
   * @return the created {@link CourseCategoryTO} object.
   */
  @PostMapping("/categories/create")
  @ResponseStatus(HttpStatus.CREATED)
  public CourseCategoryTO createCategory(@RequestBody @Valid CourseCategoryTO courseCategoryTO) {
    return categoryService.createCourseCategory(courseCategoryTO);
  }

  /**
   * Deletes the authenticated user's account.
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
