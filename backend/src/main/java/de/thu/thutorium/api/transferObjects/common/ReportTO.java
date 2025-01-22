package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportTO {

    /** The unique identifier for the report. */
    private Long reportId;

    @NotNull(message = "TutorId can't be null")
    /** The ID of the tutor who wrote the report. */
    private Long tutorId;

    @NotNull(message = "CourseId cant be null")
    /** The ID of the course associated with the report. */
    private Long courseId;

    @NotNull(message = "MeetingId cant be null")
    /** The ID of the meeting associated with the report. */
    private Long meetingId;

    @NotNull(message = "StudentId cant be null")
    /** The ID of the student who received the report. */
    private Long studentId;

    /** The full name of the tutor who wrote the report. */
    private String tutorFullName;

    /** The full name of the student who received the report. */
    private String studentFullName;

    /** The name of the course associated with the report. */
    private String courseName;

    /** The body text of the report. */
    private String text;

    /** The timestamp when the report was created. */
    private LocalDateTime createdAt;
}
