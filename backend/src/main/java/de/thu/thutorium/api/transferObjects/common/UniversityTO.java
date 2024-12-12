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

    /**
     * Unique identifier for the university.
     */
    @NotNull
    private Long universityId;

    /**
     * Name of the university.
     */
    @NotEmpty(message = "The university name cannot be empty")
    private String universityName;

    /**
     * Address of the university.
     */
    @NotNull
    private AddressTO address;
}





