package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course entity. This entity is mapped to the {@code course} table in the database.
 *
 * <p>It includes information such as the course name, description, start date, end date, etc.
 *
 * <p>Lombok annotations are used to automatically generate boilerplate code like getters, setters,
 * and constructors.
 *
 * <p>
 */
@Builder(toBuilder = true) // If Builder is intended to be used
@Entity
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor
//@EntityListeners(DBPreProcess.class) //Save all string fields in all entities in lower case
public class CourseDBO {
  /**
   * The unique identifier for the course. This value is automatically generated by the database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id")
  @Setter(AccessLevel.NONE)
  private Long courseId;

  /**
   * Participants of this course.
   *
   * <p>Defines a many-to-many relationship with {@link UserDBO} using the join table "user_course"
   * denoting the courses and the users who enrol in them.
   */
  @ManyToMany(mappedBy = "studentCourses", fetch = FetchType.LAZY)
  @Builder.Default
  private List<UserDBO> students = new ArrayList<>();

  /** The name of the course. This field is mandatory and cannot be null. */
  @Column(name = "course_name", nullable = false)
  private String courseName;

  /**
   * Defines a many-to-one relationship between courses and the tutor who created the courses. The
   * counterpart is denoted by a List<CourseDBO> tutorCourses in the {@link UserDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "tutor_id")
  private UserDBO tutor;

  /**
   * A short description of the course (1-2 sentences). This field is mandatory and cannot be null.
   */
  @Column(name = "description_short")
  private String descriptionShort;

  /** A long description of the course. This field is optional and can be null. */
  @Column(name = "description_long", columnDefinition = "TEXT")
  private String descriptionLong;

  /**
   * The timestamp when the course was created. This field is mandatory and cannot be {@code null}.
   */
  @Column(name = "created_on")
  private LocalDateTime createdOn;

  /** The start date of the course. This field is optional and can be {@code null}. */
  @Column(name = "start_date")
  private LocalDate startDate;

  /** The end date of the course. This field is optional and can be {@code null}. */
  @Column(name = "end_date")
  private LocalDate endDate;

  /**
   * Ratings received by a course from students.
   *
   * <p>Defines a one-to-many relationship with {@link RatingCourseDBO}. The {@code orphanRemoval}
   * attribute ensures that ratings are removed if they are no longer associated with the tutor.
   */
  @OneToMany(mappedBy = "course", orphanRemoval = true)
  @Builder.Default
  private List<RatingCourseDBO> receivedCourseRatings = new ArrayList<>();

  /** The average rating of the course. This field is not persisted in the database. */
  @Transient private Double averageRating;

  /** Initializes transient fields after the entity is loaded from the database. */
  @PostLoad
  private void onLoad() {
    if (receivedCourseRatings != null && !receivedCourseRatings.isEmpty()) {
      double sum = receivedCourseRatings.stream().mapToDouble(RatingCourseDBO::getPoints).sum();
      this.averageRating = sum / receivedCourseRatings.size();
    } else {
      this.averageRating = 0.0;
    }
  }

  /**
   * Meetings received for a course.
   *
   * <p>Defines a one-to-many relationship with {@link MeetingDBO}. The {@code orphanRemoval}
   * attribute ensures that meetings are removed if they are no longer associated with the course.
   */
  @OneToMany(mappedBy = "course", orphanRemoval = true)
  @Builder.Default
  private List<MeetingDBO> meetings = new ArrayList<>();

  /**
   * Progress recorded for a course.
   *
   * <p>Defines a one-to-many relationship with {@link ProgressDBO}. The {@code orphanRemoval}
   * attribute ensures that progress is removed if they are no longer associated with the course.
   */
  @OneToMany(mappedBy = "course", orphanRemoval = true)
  private List<ProgressDBO> progress;

  /** The list of course categories for a course. */
  @ManyToMany(
      mappedBy = "courses",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private List<CourseCategoryDBO> courseCategories = new ArrayList<>();

  /** Constructs a CourseDBO object with empty lists. */
  public CourseDBO() {
    this.students = new ArrayList<>();
    this.receivedCourseRatings = new ArrayList<>();
    this.meetings = new ArrayList<>();
    this.progress = new ArrayList<>();
    this.courseCategories = new ArrayList<>();
  }
}
