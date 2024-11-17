package de.thu.thutorium.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Course {
  @Id
  @SequenceGenerator(
    name="course_sequence",
    sequenceName= "course_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "course_sequence"
  )
  private Long courseId;
  private String courseName;
  private String description;

  public Course(Long courseId, String courseName, String description) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.description = description;
  }

  public Course(String courseName, String description) {
    this.courseName = courseName;
    this.description = description;
  }

  public Course() {
  }



  public Long getCourseId() {
    return courseId;
  }
  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
  public String getCourseName() {
    return courseName;
  }
  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  

}
