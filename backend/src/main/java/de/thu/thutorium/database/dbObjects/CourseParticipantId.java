package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * Composite key class for CourseParticipant, representing the unique key for course participants.
 * This class is intended to be embedded within an entity class to form a composite primary key.
 *
 * <p>Implements {@link Serializable} to allow instances of this class to be easily serialized.
 */
@Embeddable
public class CourseParticipantId implements Serializable {
  /** The ID of the user. */
  private Long userId;

  /** The ID of the course. */
  private Long courseId;
}
