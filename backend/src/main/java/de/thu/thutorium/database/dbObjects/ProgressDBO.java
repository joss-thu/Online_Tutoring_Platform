package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;


/**
 * Represents the scores achieved by a student for a course.
 *
 * <p>
 *
 * @see UserDBO
 * @see CourseDBO
 */
@Builder
@Entity
@Table(name = "progess")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDBO {
  /** Primary key of the Rating table, automatically generated. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rating_id")
  @Setter(AccessLevel.NONE)
  private Long progressId;

  /**
   * The student whose progress is being tracked.
   *
   * <p>Defines a many-to-one relationship with {@link UserDBO}. The counterpart is denoted by a
   * List<ProgressDBO> called 'receivedScores' in {@link UserDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private UserDBO student;

  /**
   * The course which received the rating.
   *
   * <p>Defines a many-to-one relationship with {@link CourseDBO}.The counterpart is denoted by
   * List<ProgressDBO> called 'progress' in {@link CourseDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "course_id", nullable = false)
  private CourseDBO course;

  /** The rating points given by the student. */
  @Column(name = "points", nullable = false)
  @Builder.Default
  private Double points = 0.0;
}
