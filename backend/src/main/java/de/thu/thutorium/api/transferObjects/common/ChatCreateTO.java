package de.thu.thutorium.api.transferObjects.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCreateTO {
    /** List of user IDs participating in the chat. */
    @NotNull
    @Size(min = 2, message = "A chat must have at least two participants.")
    private List<Long> participantIds;

    /** Optional: Title for group chats. */
    private String chatTitle;

    /** Boolean to indicate if it's a group chat. */
    @NotNull
    private Boolean isGroup;
}
