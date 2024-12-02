package de.thu.thutorium.api.transferObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/** A Data Transfer Object for the Course entity. */
@Data
public class CourseDTO {
  private Long courseId;
  private String courseName;
  private String descriptionShort;
  private String descriptionLong;
  private LocalDateTime createdAt;
  private LocalDate startDate;
  private LocalDate endDate;
  //    private CategoryDTO category;
  //    private UserDTO tutor; // Full tutor details
  //    private List<RatingDTO> ratings = new ArrayList<>(); // List of ratings associated with the
  // course
}
