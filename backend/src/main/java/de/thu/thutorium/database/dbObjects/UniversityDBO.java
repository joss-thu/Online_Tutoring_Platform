package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
  @Column(name = "university_id")
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
   * The address of the university. This establishes a uni-directional(!!) one-to-one relationship with the {@code
   * Address} entity.
   */
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "address_id", nullable = false, unique = true)
  private AddressDBO address;

  /**
   * Defines a one-to-many relationship between an affiliation and its associated affiliations.
   * This relationship is mapped by the {@code affiliation} field in the {@link AffiliationDBO} entity.
   * The cascade types {@code PERSIST}, {@code MERGE}, and {@code REFRESH} ensure that these operations
   * are propagated to the associated affiliations.
   * @see AffiliationDBO
   */
  @OneToMany(mappedBy = "university", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  private List<AffiliationDBO> affiliations;
}
