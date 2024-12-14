package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import de.thu.thutorium.services.interfaces.UniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/universities")
@RequiredArgsConstructor
public class UniversityController {
  private final UniversityService universityService;

  @PostMapping
  public ResponseEntity<UniversityDBO> createUniversity(
      @RequestBody @Valid UniversityTO university) {
    UniversityDBO created = universityService.createUniversity(university);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }
}
