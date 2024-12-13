package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transfer object representing a university.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityTO {
    @NotNull
    private Long universityId;

    @NotEmpty(message = "The university name cannot be empty")
    private String universityName;

    @NotNull
    private AddressTO address;
}
