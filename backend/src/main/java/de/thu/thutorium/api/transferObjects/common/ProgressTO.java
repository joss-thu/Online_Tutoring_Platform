package de.thu.thutorium.api.transferObjects.common;

import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

/**
 * Transfer object representing the progress of a student in a course.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressTO {

    /**
     * Unique identifier for the rating.
     */
    @NotNull(message = "Rating ID cannot be null")
    private Long ratingId;

    /**
     * The student associated with the progress.
     */
    @NotNull(message = "Student cannot be null")
    private UserDBO student;

    /**
     * The course associated with the progress.
     */
    @NotNull(message = "Course cannot be null")
    private CourseDBO course;

    /**
     * The points scored by the student in the course.
     */
    @NotNull(message = "Points cannot be null")
    @PositiveOrZero(message = "Points must be zero or positive")
    private Double points = 0.0;
}
