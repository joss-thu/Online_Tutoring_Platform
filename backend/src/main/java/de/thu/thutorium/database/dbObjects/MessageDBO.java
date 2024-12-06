package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a message entity in the database. This class is mapped to the "message" table in the
 * database using JPA annotations.
 *
 * <p>The {@code Message} class includes attributes such as the sender, receiver, message content,
 * timestamps for sending and reading, and a flag indicating whether the message has been read.
 */
@Builder
@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDBO {

  /**
   * The unique identifier for the message. This is the primary key and is auto-generated using
   * {@code GenerationType.IDENTITY}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long messageId;

  /**
   * The sender of the message.
   * <p> Defines a many-to-one relationship with {@link UserDBO}. The counterpart is denoted by a
   * List<MessageDBO> called 'messages_sent' in {@link UserDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false)
  private UserDBO sender;

  /**
   * The receiver of the message.
   * <p> Defines a many-to-one relationship with {@link UserDBO}.The counterpart is denoted by a
   * List<MessageDBO> called 'messages_received' in {@link UserDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "receiver_id", nullable = false)
  private UserDBO receiver;

  /** The content of the message. Stored as a TEXT column in the database. */
  @Column(name = "message_content", columnDefinition = "TEXT", nullable = false)
  private String messageContent;

  /** The timestamp when the message was sent. */
  @Column(name = "send_at", nullable = false)
  private LocalDateTime sendAt;

  /** The timestamp when the message was read. This field is optional. */
  @Column(name = "read_at")
  private LocalDateTime readAt;

  /** Flag indicating whether the message has been read. */
  @Column(name = "is_read", nullable = false)
  private Boolean isRead=false;
}
