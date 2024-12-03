package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "meeting")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Represents a meeting entity in the system, which includes details about a particular meeting such
 * as its date, type, status, related course, and more.
 *
 * <p>This entity is mapped to the "meeting" table in the database. Each meeting has a unique ID,
 * and is associated with a course and an address. The meeting also includes fields for date, type,
 * status, room number, duration of the meeting, and a link to the meeting.
 *
 * @entity
 * @table name="meeting"
 */
public class Meeting {
  /** The unique identifier for the meeting. This ID is generated automatically. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "meeting_id")
  private Long meetingId;
//
//  /**
//   * The course to which this meeting is related. The association is managed as a many-to-one
//   * relationship.
//   */
//  @ManyToOne
//  @JoinColumn(name = "course_id")
//  private Course course;
//
//  /** The date on which the meeting is scheduled to be held. This field cannot be null. */
//  @Column(name = "meeting_date", nullable = false)
//  private LocalDate meetingDate;
//
//  /**
//   * The type of the meeting (e.g., lecture, tutorial, etc.). Must be a non-null string with a
//   * maximum length of 255 characters.
//   */
//  @Column(name = "meeting_type", nullable = false, length = 255)
//  private String meetingType;
//
//  /**
//   * The current status of the meeting (e.g., confirmed, canceled, etc.). Must be a non-null string
//   * with a maximum length of 255 characters.
//   */
//  @Column(name = "meeting_status", nullable = false, length = 255)
//  private String meetingStatus;
//
//  /**
//   * The room number where the meeting is taking place, if applicable. This field may be null and
//   * can have a length of up to 50 characters.
//   */
//  @Column(name = "room_number", length = 50)
//  private String roomNum;
//
//  /** The address where the meeting is being held. This is managed as a many-to-one relationship. */
//  @ManyToOne
//  @JoinColumn(name = "address_id")
//  private Address address;
//
//  /** The duration of the meeting in minutes. This field cannot be null. */
//  @Column(name = "duration", nullable = false)
//  private Integer duration;
//
//  /**
//   * The link to access the meeting, often in the form of a URL. This is stored as text in the
//   * database.
//   */
//  @Column(name = "meeting_link", columnDefinition = "TEXT")
//  private String meetingLink;
//
//  @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
//  private List<User> meetingParticipants;
}
