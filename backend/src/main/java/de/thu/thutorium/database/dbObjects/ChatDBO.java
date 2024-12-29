package de.thu.thutorium.database.dbObjects;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a chat in the system.
 *
 * <p>This class defines the properties and relationships of a chat, including the list of
 * participants, the chat's creation timestamp, the flag for group chat status, the chat's title,
 * and associated messages.
 *
 * <p>Annotations used:
 *
 * <ul>
 *   <li>@Builder: Generates a builder for the class, allowing fluent construction of objects.
 *   <li>@Entity: Marks this class as a JPA entity.
 *   <li>@Table: Specifies the table name in the database.
 *   <li>@Getter and @Setter: Generate getter and setter methods for all fields.
 *   <li>@NoArgsConstructor and @AllArgsConstructor: Generate constructors for the class.
 *   <li>@ManyToMany: Specifies a many-to-many relationship between chat and user entities.
 *   <li>@OneToMany: Specifies a one-to-many relationship between chat and message entities.
 * </ul>
 */
@Builder
@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDBO {

  /** The unique identifier for the chat. This is the primary key. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chatId;

  /**
   * The list of participants in the chat.
   *
   * <p>Defines a many-to-many relationship with {@link UserDBO}.
   */
  @ManyToMany
  @JoinTable(
      name = "chat_participants",
      joinColumns = @JoinColumn(name = "chat_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  @Builder.Default
  private Set<UserDBO> participants = new HashSet<>();

  /** The timestamp when the chat was created. */
  @Column(name = "created_at", nullable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  /** A flag to indicate whether the chat is a group chat. */
  @Column(name = "is_group", nullable = false)
  @Builder.Default
  private Boolean isGroup = false;

  /** The title of the group chat (optional for one-on-one chats). */
  @Column(name = "chat_title")
  private String chatTitle;

  /**
   * A list of messages associated with this chat. Defines a one-to-many relationship with {@link
   * MessageDBO}.
   */
  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<MessageDBO> messages = new ArrayList<>();
}
