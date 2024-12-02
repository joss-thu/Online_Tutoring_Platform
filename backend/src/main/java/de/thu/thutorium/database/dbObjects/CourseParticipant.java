package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a participant in a course, linking a user to a course. This class is mapped to the
 * "course_participants" table in the database and uses a composite key to represent the
 * relationship between a user and a course.
 *
 * <p>The {@code CourseParticipant} class establishes many-to-one relationships with both the {@code
 * User} and {@code Course} entities. The primary key is a composite key, represented by the {@code
 * CourseParticipantId} class.
 */
@Entity
@Table(name = "course_participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseParticipant {
  /**
   * The composite primary key for the course participant. This key is an embedded object, defined
   * in the {@code CourseParticipantId} class, which consists of the user ID and course ID.
   */
  @EmbeddedId private CourseParticipantId id;

  /**
   * The user participating in the course. This is a many-to-one relationship, and the {@code
   * userId} from the composite key is mapped to the {@code User} entity.
   */
  @ManyToOne
  @MapsId("userId") // Maps to userId in the composite key
  @JoinColumn(name = "user_id")
  private User user;

  /**
   * The course in which the user is participating. This is a many-to-one relationship, and the
   * {@code courseId} from the composite key is mapped to the {@code Course} entity.
   */
  @ManyToOne
  @MapsId("courseId") // Maps to courseId in the composite key
  @JoinColumn(name = "course_id")
  private Course course;
}
