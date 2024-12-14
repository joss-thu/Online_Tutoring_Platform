package de.thu.thutorium.api.transferObjects.admin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class AdminUniversityTO {
    @NotEmpty(message = "The university name cannot be empty")
    private String universityName;

    @NotNull
    private AdminAddressTO address;
}
