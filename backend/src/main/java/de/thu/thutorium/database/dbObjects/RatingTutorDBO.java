package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a rating given by a student for a tutor.
 *
 * <p>This entity stores the details of the rating, including the student who gave the rating, the
 * tutor being rated, the rating points (e.g., 1.0 to 5.0), and an optional review text.
 * @see UserDBO
 */
@Entity
@Table(name = "rating_tutor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingTutorDBO {
  /** Primary key of the Rating table, automatically generated. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rating_id")
  @Setter(AccessLevel.NONE)
  private Long ratingId;

  /**
   * The student who gave the rating.
   * <p> Defines a many-to-one relationship with {@link UserDBO}. The counterpart is denoted by Set<RatingTutorDBO>
   * 'givenTutorRatings' in {@link UserDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private UserDBO student;

  /**
   * The tutor who received the rating.
   * <p> Defines a many-to-one relationship with {@link UserDBO}.The counterpart is denoted by
   * Set<RatingTutorDBO> 'receivedTutorRatings' in {@link UserDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "tutor_id", nullable = false)
  private UserDBO tutor;

  /**
   * The rating points given by the student.
   */
  @Column(name = "points", nullable = false)
  private Double points;

  /**
   * The review text provided by the student.
   */
  @Column(name = "review", columnDefinition = "TEXT")
  private String review;

  /**
   * The timestamp when the rating was created.
   */
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();
}
