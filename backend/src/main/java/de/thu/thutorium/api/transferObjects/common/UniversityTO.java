package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a university.
 *
 * <p>This class is used to transfer data related to a university, including its name and address.
 *
 * <p>Annotations used:
 *
 * <ul>
 *   <li>@Getter and @Setter: Generate getter and setter methods for all fields.
 *   <li>@NoArgsConstructor: Generates a no-argument constructor.
 *   <li>@AllArgsConstructor: Generates a constructor with arguments for all fields.
 *   <li>@NotEmpty: Validates that the field is not empty.
 *   <li>@Valid: Validates the nested {@link AddressTO} object.
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityTO {
  /** The name of the university. This field cannot be empty. */
  @NotEmpty(message = "The university name cannot be empty")
  private String universityName;

  /**
   * The address of the university. This field is validated as a nested object using the {@link
   * AddressTO} class.
   */
  @Valid private AddressTO address;
}
