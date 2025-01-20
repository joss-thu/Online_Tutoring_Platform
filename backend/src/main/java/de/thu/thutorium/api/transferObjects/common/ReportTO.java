package de.thu.thutorium.api.transferObjects.common;

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

    /** The ID of the tutor who wrote the report. */
    private Long tutorId;

    /** The ID of the course associated with the report. */
    private Long courseId;

    /** The ID of the meeting associated with the report. */
    private Long meetingId;

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
