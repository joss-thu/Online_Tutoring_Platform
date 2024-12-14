package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Transfer object representing a university.
 */
public class UniversityTO {
    @NotEmpty(message = "The university name cannot be empty")
    private String universityName;

    @Valid
    private AddressTO address;
}
