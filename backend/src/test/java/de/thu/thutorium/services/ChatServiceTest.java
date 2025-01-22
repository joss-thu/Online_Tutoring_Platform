package de.thu.thutorium.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import de.thu.thutorium.database.DBOMappers.ChatDBMapper;
import de.thu.thutorium.database.dbObjects.ChatDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.ChatRepository;
import de.thu.thutorium.database.repositories.MessageRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.ChatServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatDBMapper chatMapper;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatServiceImpl chatService;

    private UserDBO createUser(Long id, String firstName, String lastName) {
        return UserDBO.builder()
                .userId(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com")
                .password("password123")
                .isVerified(true)
                .enabled(true)
                .build();
    }

    private UserDBO user1;
    private UserDBO user2;
    private ChatCreateTO chatCreateTO;

    @BeforeEach
    public void setUp() {
        user1 = createUser(1L, "John", "Doe");
        user2 = createUser(2L, "Jane", "Doe");

        chatCreateTO = new ChatCreateTO();
        chatCreateTO.setParticipantIds(Arrays.asList(1L, 2L));
    }

    @Test
    public void testCreateChat_ParticipantNotFound() {
        // Given: Empty participant list from the repository
        lenient().when(userRepository.findAllById(chatCreateTO.getParticipantIds()))
                .thenReturn(Collections.emptyList());

        // Then: EntityNotFoundException should be thrown
        assertThrows(EntityNotFoundException.class, () -> chatService.createChat(chatCreateTO));

        // Verify no interaction with the repository save method
        verify(chatRepository, never()).save(any(ChatDBO.class));
    }


    @Test
    public void testDeleteChat_Success() {
        // Given
        Long chatId = 1L;
        ChatDBO chatDBO = new ChatDBO();
        chatDBO.setChatId(chatId);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chatDBO));

        // When
        chatService.deleteChat(chatId);

        // Then
        verify(chatRepository, times(1)).delete(chatDBO);
    }

    @Test
    public void testDeleteChat_ChatNotFound() {
        // Given: Chat is not found in the repository
        Long chatId = 1L;
        when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

        // Then: EntityNotFoundException should be thrown
        assertThrows(EntityNotFoundException.class, () -> chatService.deleteChat(chatId));

        // Verify no interaction with the repository delete method
        verify(chatRepository, never()).delete(any(ChatDBO.class));
    }

    @Test
    public void testCreateChat_InvalidParticipantData() {
        // Given: A participant with a null ID (invalid data)
        chatCreateTO.setParticipantIds(Arrays.asList(1L, null));

        // Simulate that the repository only finds one user, missing the second participant
        lenient().when(userRepository.findAllById(chatCreateTO.getParticipantIds()))
                .thenReturn(Collections.singletonList(user1)); // Missing user2

        // Then: EntityNotFoundException should be thrown
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> chatService.createChat(chatCreateTO));

        assertEquals("Creator not found", exception.getMessage()); // Updated expected message

        // Verify no interaction with the repository save method
        verify(chatRepository, never()).save(any(ChatDBO.class));
    }


    @Test
    public void testDeleteChat_AlreadyDeleted() {
        // Given
        Long chatId = 1L;
        ChatDBO chatDBO = new ChatDBO();
        chatDBO.setChatId(chatId);

        when(chatRepository.findById(chatId))
                .thenReturn(Optional.of(chatDBO))
                .thenReturn(Optional.empty()); // Simulate chat already deleted

        // When
        chatService.deleteChat(chatId);

        // Then
        verify(chatRepository, times(1)).delete(chatDBO);

        // When: Attempt to delete again
        assertThrows(EntityNotFoundException.class, () -> chatService.deleteChat(chatId));
    }
}