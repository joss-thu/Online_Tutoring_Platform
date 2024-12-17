package de.thu.thutorium.database.dbObjects;

import de.thu.thutorium.database.dbObjects.enums.AffiliationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents an affiliation between a university and an affiliation type. This class is mapped to
 * the "affiliation" table in the database.
 *
 * <p>The {@code Affiliation} class links a university with an affiliation type, and includes an ID
 * that uniquely identifies each affiliation. The affiliation type is represented as an enum.
 */
@Entity
@Table(name = "affiliation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationDBO {

  /**
   * The unique identifier for the affiliation. This is the primary key and is auto-generated using
   * {@code GenerationType.IDENTITY}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "affiliation_id")
  @Setter(AccessLevel.NONE)
  private Long affiliationId;

  /**
   * The type of affiliation (e.g., faculty, student, etc.). This is an enum value, and is stored as
   * a string in the database. The field is required (nullable = false).
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "affiliation_type", nullable = false)
  private AffiliationType affiliationType;

  /**
   * The university associated with the affiliation. This is a many-to-one relationship with the
   * {@code University} entity. The counterpart is denoted by a List<AffiliationDBO> called
   * 'affiliations' in {@code University}
   */
  @ManyToOne
  @JoinColumn(name = "university_id")
  private UniversityDBO university;

  /**
   * Users affiliated with this affiliation.
   * <p>Defines a one-to-many relationship with {@link UserDBO}.
   */
  @OneToMany(mappedBy = "affiliation")
  private List<UserDBO> affiliatedUsers;
}
