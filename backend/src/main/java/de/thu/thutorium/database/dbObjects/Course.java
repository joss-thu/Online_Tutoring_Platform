package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a course entity within the system. This entity is mapped to the {@code course} table
 * in the database.
 *
 * <p>The course is associated with a tutor (user) and belongs to a specific category. It includes
 * information such as the course name, description, start date, end date, and the timestamp of when
 * the course was created.
 *
 * <p>Lombok annotations are used to automatically generate boilerplate code like getters, setters,
 * and constructors.
 *
 * <p>// * @see User
 *
 * @see Category
 */
@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

  /**
   * The unique identifier for the course. This value is automatically generated by the database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id")
  private Long courseId;

  //    /**
  //     * The tutor responsible for teaching the course.
  //     *
  //     * <p>This relationship is mapped by the {@code tutor} field in the {@link User} entity.
  // Deleting
  //     * a course does not delete the associated tutor.
  //     */
  //    @ManyToOne
  //    @JoinColumn(name = "tutor_id", nullable = false)
  //    private User tutor;

  /** The name of the course. This field is mandatory and cannot be null. */
  @Column(name = "course_name", nullable = false)
  private String courseName;

  /**
   * A short description of the course (1-2 sentences). This field is mandatory and cannot be null.
   */
  @Column(name = "description_short", nullable = false, length = 170)
  private String descriptionShort;

  /** A long description of the course. This field is optional and can be null. */
  @Column(name = "description_long", length = 1500)
  private String descriptionLong;

  /**
   * The timestamp when the course was created. This field is mandatory and cannot be {@code null}.
   */
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  /** The start date of the course. This field is optional and can be {@code null}. */
  @Column(name = "start_date")
  private LocalDate startDate;

  /** The end date of the course. This field is optional and can be {@code null}. */
  @Column(name = "end_date")
  private LocalDate endDate;

  /**
   * The category to which the course belongs. This relationship is mapped by the {@code category}
   * field in the {@link Category} entity. This field is mandatory and cannot be null.
   */
  @ManyToOne
  @JoinColumn(name = "course_category_id", nullable = false)
  private Category category;

  /**
   * Represents the list of ratings associated with this course.
   *
   * <p>This relationship is mapped by the {@code course} field in the {@link Rating} entity. The
   * cascade type {@code CascadeType.ALL} ensures that all operations (such as persist and remove)
   * are propagated to the associated ratings. Additionally, {@code orphanRemoval = true} guarantees
   * that ratings that are no longer associated with this course will be automatically deleted.
   *
   * <p>If the course is deleted, all ratings associated with it will also be deleted due to the
   * cascading operations defined in this relationship.
   *
   * <p>The {@link Rating} entity references the {@code course} field, and we use the
   * {@code @JsonIgnoreProperties} annotation to prevent infinite recursion during serialization.
   * This annotation ensures that the fields {@code course}, {@code tutor}, and {@code
   * ratingsAsStudents} in the {@link Rating} entity are ignored during JSON serialization to avoid
   * circular dependencies.
   *
   * @see Rating
   * @see Course
   */
  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Rating> ratings;
}
