package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.services.implementations.UniversityServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing admin-related operations.
 * This controller provides endpoints for creating and managing university
 * entities.
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final UniversityServiceImpl universityService;

    /**
     * Creates a new university.
     * This endpoint accepts a {@link UniversityTO} object in the request body and
     * creates a new university entity.
     * The request body is validated using the {@link Valid} annotation.
     *
     * @param university the {@code UniversityTO} object containing the university
     *                   data
     * @return a {@code ResponseEntity} containing the created {@code UniversityTO}
     *         object and a {@link HttpStatus#CREATED} status
     */
    @PostMapping("create-university")
    public ResponseEntity<UniversityTO> createUniversity(@Valid @RequestBody UniversityTO university) {
        UniversityTO created = universityService.createUniversity(university);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
