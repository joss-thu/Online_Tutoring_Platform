package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an address entity in the database. This class is mapped to the "address" table in the
 * database using JPA annotations.
 *
 * <p>The {@code Address} class includes attributes for a typical address such as house number,
 * street name, city, postal code, and country.
 *
 * <p>This class uses Lombok annotations to reduce boilerplate code for getter, setter, equals,
 * hashCode, and toString methods.
 *
 * <p>Annotations: - {@code @Entity} - Marks this class as a JPA entity. - {@code @Table(name =
 * "address")} - Maps this entity to the "address" table in the database. - {@code @Data} - Lombok
 * annotation to generate getters, setters, and other utility methods. - {@code @NoArgsConstructor}
 * - Lombok annotation to generate a no-argument constructor. - {@code @AllArgsConstructor} - Lombok
 * annotation to generate an all-argument constructor.
 *
 * <p>Attributes: - {@code addressId} - The primary key for the address, auto-generated. - {@code
 * houseNum} - The house number, must not be null. - {@code streetName} - The name of the street,
 * must not be null. - {@code city} - The name of the city, must not be null. - {@code postalCode} -
 * The postal code, must not be null. - {@code country} - The name of the country, must not be null.
 */
@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  /**
   * The unique identifier for the address. This is the primary key and is auto-generated using
   * {@code GenerationType.IDENTITY}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id")
  private Long addressId;

  /** The house number for the address. This field is required and cannot be null. */
  @Column(name = "house_number", nullable = false)
  private Short houseNum;

  /** The name of the street for the address. This field is required and cannot be null. */
  @Column(name = "street_name", nullable = false)
  private String streetName;

  /** The city for the address. This field is required and cannot be null. */
  @Column(name = "city", nullable = false)
  private String city;

  /** The postal code for the address. This field is required and cannot be null. */
  @Column(name = "postal_code", nullable = false)
  private Short postalCode;

  /** The country for the address. This field is required and cannot be null. */
  @Column(name = "country", nullable = false)
  private String country;
}
