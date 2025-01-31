package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDBO {

    /** The unique identifier for the report. This value is automatically generated by the database. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    /** The tutor who wrote the report. A tutor can write many reports. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private UserDBO tutor;

    /** The student who received the report. A student can receive many reports. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserDBO student;

    /** The course for which the report was written. A course can have many reports. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseDBO course;

    /** The meeting associated with the report. One report per meeting. */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false, unique = true)
    private MeetingDBO meeting;

    /** The body of the report. */
    @Column(name = "report_text", columnDefinition = "TEXT", nullable = false)
    private String text;

    /** The timestamp when the report was created. */
    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
