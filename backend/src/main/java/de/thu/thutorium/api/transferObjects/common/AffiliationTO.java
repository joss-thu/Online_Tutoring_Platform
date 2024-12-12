package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transfer object representing an affiliation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationTO {
    /**
     * Unique identifier for the affiliation.
     */
    @NotNull
    private Long affiliationId;
    /**
     * Type of the affiliation.
     */
    @NotEmpty(message = "The affiliation type cannot be empty")
    private String affiliationType;
    /**
     * University associated with the affiliation.
     */
    @NotEmpty(message = "The university cannot be empty")
    private UniversityTO university;
}
