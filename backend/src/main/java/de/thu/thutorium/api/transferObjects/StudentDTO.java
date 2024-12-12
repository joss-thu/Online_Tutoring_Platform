package de.thu.thutorium.api.transferObjects;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a student in the system.
 *
 * <p>This class extends the {@code UserBaseDTO} and is intended to represent student-specific data.
 * Currently, it inherits all fields from {@code UserBaseDTO} and does not contain additional
 * fields, but can be extended in the future to include student-specific attributes.
 */
@Data
public class StudentDTO extends UserBaseDTO {
  // student-specific fields
}
