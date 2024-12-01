package de.thu.thutorium.api.transferObjects;

import java.time.LocalDate;

public class CourseCreateDTO {
  private String courseName;
  private String descriptionShort;
  private String descriptionLong;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long tutorId; // ID only, to fetch the tutor in the service layer
  private Long categoryId; // ID only, to fetch the category in the service layer
}
