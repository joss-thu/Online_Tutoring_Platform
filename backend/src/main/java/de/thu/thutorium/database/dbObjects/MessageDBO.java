package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDBO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long messageId;

  /**
   * The chat this message belongs to.
   * Defines a many-to-one relationship with {@link ChatDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "chat_id", nullable = false)
  private ChatDBO chat;

  /**
   * The sender of the message.
   * Defines a many-to-one relationship with {@link UserDBO}.
   */
  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false)
  private UserDBO sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id", nullable = false)
  private UserDBO receiver;

  /**
   * The content of the message.
   */
  @Column(name = "message_content", columnDefinition = "TEXT", nullable = false)
  private String messageContent;

  /**
   * The timestamp when the message was sent.
   */
  @Column(name = "send_at", nullable = false)
  private LocalDateTime sendAt;

  /**
   * Indicates whether the message has been read.
   */
  @Column(name = "is_read", nullable = false)
  @Builder.Default
  private Boolean isRead = false;

  /**
   * The timestamp when the message was read.
   */
  @Column(name = "read_at")
  private LocalDateTime readAt;
}

