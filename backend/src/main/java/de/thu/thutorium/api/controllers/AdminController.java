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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
  private final UniversityService universityService;
  private final AddressService addressService;
  private final UserService userService; // Service to interact with the User repository
  private final ChatService chatService;
  private final CategoryService categoryService;

  @PostMapping("create-university")
  public ResponseEntity<UniversityTO> createUniversity(
      @Valid @RequestBody UniversityTO university) {
    UniversityTO created = universityService.createUniversity(university);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PostMapping("create-address")
  public ResponseEntity<AddressTO> createAddress(@Valid @RequestBody AddressTO address) {
    AddressTO created = addressService.createAddress(address);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @DeleteMapping("/delete-user/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId); // Delegate the delete operation to the service
    return ResponseEntity.ok("User deleted successfully");
  }

  @PostMapping("/chat-create")
  public ResponseEntity<String> createChat(@RequestBody @Valid ChatCreateTO requestDTO) {
    chatService.createChat(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body("Chat created successfully!");
  }

  @DeleteMapping("/chat-delete/{chatId}")
  public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
    chatService.deleteChat(chatId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Chat deleted successfully!");
  }

  @PostMapping("/categories/create")
  @ResponseStatus(HttpStatus.CREATED)
  public CourseCategoryTO createCategory(@RequestBody @Valid CourseCategoryTO courseCategoryTO) {
    return categoryService.createCourseCategory(courseCategoryTO);
  }
}
