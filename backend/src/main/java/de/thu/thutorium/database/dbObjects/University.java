package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a university entity in the database. This class is mapped to the "university" table in
 * the database using JPA annotations.
 *
 * <p>The {@code University} class includes attributes such as the university name, address, phone
 * number, fax number, and email. It establishes a many-to-one relationship with the {@code Address}
 * entity.
 */
@Entity
@Table(name = "university")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class University {
  /**
   * The unique identifier for the university. This is the primary key and is auto-generated using
   * {@code GenerationType.IDENTITY}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "university_name")
  private Integer universityId;

  /**
   * The name of the university. This field is required and has a maximum length of 255 characters.
   */
  @Column(name = "university_name", nullable = false, length = 255)
  private String name;

  /**
   * The address of the university. This establishes a many-to-one relationship with the {@code
   * Address} entity.
   */
  @ManyToOne
  @JoinColumn(name = "address_id", nullable = false)
  private Address address;

  /**
   * The phone number of the university. This field is required and has a maximum length of 255
   * characters.
   */
  @Column(name = "phone_number", nullable = false, length = 255)
  private String phoneNumber;

  /**
   * The fax number of the university. This field is required and has a maximum length of 255
   * characters.
   */
  @Column(name = "fax_number", nullable = false, length = 255)
  private String faxNumber;

  /**
   * The email address of the university. This field is required and has a maximum length of 255
   * characters.
   */
  @Column(name = "email_address", nullable = false, length = 255)
  private String emailAddress;
}
