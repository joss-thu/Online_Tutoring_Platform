package de.thu.thutorium;

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
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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

    private UserDBO user1;
    private UserDBO user2;
    private ChatCreateTO chatCreateTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock users
        user1 = new UserDBO();
        user1.getUserId();
        user1.setFirstName("John");
        user1.setLastName("Doe");

        user2 = new UserDBO();
        user2.getUserId();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");

        // Create ChatCreateTO with participants
        chatCreateTO = new ChatCreateTO();
        chatCreateTO.setParticipantIds(Arrays.asList(1L, 2L));
    }

    @Test
    public void testCreateChat_Success() {
        // Given
        Set<UserDBO> participants = new HashSet<>(Arrays.asList(user1, user2));
        ChatDBO chatDBO = new ChatDBO();

        // When
        when(userRepository.findAllById(chatCreateTO.getParticipantIds())).thenReturn((List<UserDBO>) participants);
        when(chatMapper.toEntity(chatCreateTO, participants)).thenReturn(chatDBO);

        chatService.createChat(chatCreateTO);

        // Then
        verify(chatRepository, times(1)).save(chatDBO);
    }

    @Test
    public void testCreateChat_ParticipantNotFound() {
        // Given
        when(userRepository.findAllById(chatCreateTO.getParticipantIds())).thenReturn((List<UserDBO>) new HashSet<UserDBO>());

        // Then
        assertThrows(EntityNotFoundException.class, () -> chatService.createChat(chatCreateTO));
    }

    @Test
    public void testDeleteChat_Success() {
        // Given
        Long chatId = 1L;
        ChatDBO chatDBO = new ChatDBO();
        chatDBO.setChatId(chatId);

        // When
        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chatDBO));

        chatService.deleteChat(chatId);

        // Then
        verify(chatRepository, times(1)).delete(chatDBO);
    }

    @Test
    public void testDeleteChat_ChatNotFound() {
        // Given
        Long chatId = 1L;

        // When
        when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

        // Then
        assertThrows(EntityNotFoundException.class, () -> chatService.deleteChat(chatId));
    }
}
