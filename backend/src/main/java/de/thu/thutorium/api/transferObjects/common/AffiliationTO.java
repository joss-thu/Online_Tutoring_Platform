package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing an affiliation.
 *
 * <p>This class is used to transfer affiliation-related data between layers of the application. It
 * includes details about the type of affiliation and the associated university.
 *
 * <p>Annotations used:
 *
 * <ul>
 *   <li>@Getter and @Setter: Generate getter and setter methods for all fields.
 *   <li>@NoArgsConstructor: Generates a no-argument constructor.
 *   <li>@AllArgsConstructor: Generates a constructor with arguments for all fields.
 *   <li>@NotEmpty: Validates that the field is not empty.
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationTO {

  /**
   * The type of the affiliation (e.g., "Student", "Faculty", "Alumni"). This field cannot be empty.
   */
  @NotEmpty(message = "The affiliation type cannot be empty")
  private String affiliationType;

  /** The university associated with this affiliation. This field cannot be empty. */
  @NotEmpty(message = "The university cannot be empty")
  private UniversityTO university;
}
