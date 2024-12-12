package de.thu.thutorium.api.transferObjects;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for creating a new course.
 *
 * <p>This class holds the necessary information required to create a new course in the system. It
 * includes the course name, descriptions, start and end dates, as well as references to the tutor
 * and course category using their respective IDs.
 */
public class CourseCreateDTO {
  /**
   * The name of the course to be created.
   *
   * <p>This field holds the name or title of the course that will be displayed to potential
   * students.
   */
  private String courseName;

  /**
   * A short description of the course.
   *
   * <p>This field provides a brief overview of what the course covers, typically used in listings
   * or summaries.
   */
  private String descriptionShort;

  /**
   * A detailed description of the course.
   *
   * <p>This field contains a comprehensive explanation of the course, including course objectives,
   * content, and learning outcomes.
   */
  private String descriptionLong;

  /**
   * The start date of the course.
   *
   * <p>This field indicates when the course will begin, providing the students with the course's
   * availability date.
   */
  private LocalDate startDate;

  /**
   * The end date of the course.
   *
   * <p>This field specifies when the course will end, allowing students to plan around the course's
   * conclusion.
   */
  private LocalDate endDate;

  /**
   * The ID of the tutor who will be teaching the course.
   *
   * <p>This field contains the ID of the tutor. It is used to link the course to the tutor who
   * created or is assigned to teach the course. The tutor's details will be fetched in the service
   * layer.
   */
  private Long tutorId;

  /**
   * The ID of the course category to which this course belongs.
   *
   * <p>This field contains the ID of the course category (e.g., "Math", "Science", etc.). It is
   * used to categorize the course in the system. The category details will be fetched in the
   * service layer.
   */
  private Long categoryId;
}
