package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an affiliation between a university and an affiliation type. This class is mapped to
 * the "affiliation" table in the database.
 *
 * <p>The {@code Affiliation} class links a university with an affiliation type, and includes an ID
 * that uniquely identifies each affiliation. The affiliation type is represented as an enum.
 */
@Entity
@Table(name = "affiliation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Affiliation {

  /**
   * The unique identifier for the affiliation. This is the primary key and is auto-generated using
   * {@code GenerationType.IDENTITY}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "affiliation_id")
  private Integer affiliationId;

  /**
   * The university associated with the affiliation. This is a many-to-one relationship with the
   * {@code University} entity.
   */
  @ManyToOne
  @JoinColumn(name = "university_id")
  private University university;

  /**
   * The type of affiliation (e.g., faculty, student, etc.). This is an enum value, and is stored as
   * a string in the database. The field is required (nullable = false).
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "affiliation_type", nullable = false)
  private AffiliationType ratingType;
}
