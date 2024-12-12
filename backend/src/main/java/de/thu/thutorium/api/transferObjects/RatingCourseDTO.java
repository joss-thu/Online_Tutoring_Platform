package de.thu.thutorium.api.transferObjects;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a rating given to a course by a student.
 *
 * <p>This class is used to transfer information related to a course's rating, including the rating
 * points, review text, the timestamp when the rating was created, and basic information about the
 * student who provided the rating.
 */
@Data
public class RatingCourseDTO {
  /**
   * The unique identifier for the rating.
   *
   * <p>This field represents the unique ID for the rating, which is typically used for identifying
   * and retrieving specific ratings from the system.
   */
  private Long ratingId;

  /**
   * The rating points given to the course.
   *
   * <p>This field stores the numeric value representing the rating points given by the student.
   * Typically, this might be a value between 1 and 5, depending on the rating system.
   */
  private Double points;

  /**
   * The review text provided by the student.
   *
   * <p>This field contains the feedback or comments written by the student regarding the course. It
   * may include details such as the course content, teaching quality, and overall experience.
   */
  private String review;

  /**
   * The timestamp when the rating was created.
   *
   * <p>This field stores the date and time when the rating was submitted by the student, allowing
   * the system to track when the review was made.
   */
  private LocalDateTime createdAt;

  /**
   * Basic information about the student who gave the rating.
   *
   * <p>This field contains a {@code UserBaseDTO} object that represents the student who provided
   * the rating. The student's basic information (such as their name) is included in this object.
   */
  private UserBaseDTO student;
}
