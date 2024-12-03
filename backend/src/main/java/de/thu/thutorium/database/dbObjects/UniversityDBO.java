package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Represents a university entity in the database. This class is mapped to the "university" table in
 * the database using JPA annotations.
 *
 * <p>The {@code University} class includes attributes such as the university name, address, phone
 * number, fax number, and email. It establishes a one-to-one relationship with the {@code Address}
 * entity.
 */
@Entity
@Table(name = "university")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDBO {
  /**
   * The unique identifier for the university. This is the primary key and is auto-generated using
   * {@code GenerationType.IDENTITY}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "university_name")
  @Setter(AccessLevel.NONE)
  private Integer universityId;

  /**
   * The name of the university. This field is required and has a maximum length of 255 characters.
   */
  @Column(name = "university_name", nullable = false, length = 255)
  private String name;

  /**
   * The phone number of the university. This field is optional and has a maximum length of 255
   * characters.
   */
  @Column(name = "phone_number", length = 255)
  private String phoneNumber;

  /**
   * The fax number of the university. This field is optional and has a maximum length of 255
   * characters.
   */
  @Column(name = "fax_number", length = 255)
  private String faxNumber;

  /**
   * The email address of the university. This field is required and has a maximum length of 255
   * characters.
   */
  @Column(name = "email_address", nullable = false, length = 255)
  private String emailAddress;

  /**
   * The address of the university. This establishes a one-to-one relationship with the {@code
   * Address} entity.
   */
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "address_id", nullable = false, unique = true)
  private AddressDBO address;

  /**
   * Defines a bidirectional one-to-many relationship between a university and
   * its associated universities.
   * <p>
   * This relationship is mapped by the {@code university} field in the {@link UniversityDBO} entity. The
   * {@code CascadeType.ALL} cascade type ensures that all operations (such as persist, merge, remove, refresh, and
   * detach) are propagated from the universities to their corresponding affiliations.
   * <p>
   * The {@code mappedBy} attribute specifies that the {@code university} field in the {@link UniversityDBO}
   * entity owns the relationship. This attribute is used to establish a bidirectional relationship, ensuring
   * that both sides of the relationship are aware of each other.
   * <p>
   * If the parent university entity is deleted, all associated affiliations will also be deleted due to the
   * cascading operations defined in this relationship.
   * @see UniversityDBO
   */
  @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
  private Set<UniversityDBO> universities;
}
