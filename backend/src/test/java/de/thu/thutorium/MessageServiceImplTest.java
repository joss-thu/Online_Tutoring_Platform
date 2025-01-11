package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.MessageTOMapper;
import de.thu.thutorium.api.transferObjects.common.MessageTO;
import de.thu.thutorium.database.dbObjects.ChatDBO;
import de.thu.thutorium.database.dbObjects.MessageDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.ChatRepository;
import de.thu.thutorium.database.repositories.MessageRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageTOMapper messageTOMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMessage_NullMessageTO() {
        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> messageService.saveMessage(null),
                "MessageTO cannot be null"
        );
        verifyNoInteractions(chatRepository, userRepository, messageRepository);
    }

    @Test
    void testSaveMessage_ChatNotFound() {
        // Arrange
        MessageTO inputTO = new MessageTO();
        inputTO.setChatId(999L);

        when(chatRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> messageService.saveMessage(inputTO)
        );
        assertTrue(ex.getMessage().contains("Chat not found for ID: 999"));
        verify(chatRepository).findById(999L);
        verifyNoMoreInteractions(chatRepository);
        verifyNoInteractions(userRepository, messageRepository);
    }

    @Test
    void testSaveMessage_SenderNotFound() {
        MessageTO inputTO = new MessageTO();
        inputTO.setChatId(10L);
        inputTO.setSenderId(999L);
        inputTO.setReceiverId(200L);

        ChatDBO mockChat = new ChatDBO();
        mockChat.setChatId(10L);

        when(chatRepository.findById(10L)).thenReturn(Optional.of(mockChat));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> messageService.saveMessage(inputTO)
        );
        assertTrue(ex.getMessage().contains("Sender not found for ID: 999"));
        verify(chatRepository).findById(10L);
        verify(userRepository).findById(999L);
        verifyNoInteractions(messageRepository);
    }

    @Test
    void testSaveMessage_ReceiverNotFound() {
        MessageTO inputTO = new MessageTO();
        inputTO.setChatId(10L);
        inputTO.setSenderId(100L);
        inputTO.setReceiverId(999L);

        ChatDBO mockChat = new ChatDBO();
        mockChat.setChatId(10L);
        UserDBO mockSender = new UserDBO();

        when(chatRepository.findById(10L)).thenReturn(Optional.of(mockChat));
        when(userRepository.findById(100L)).thenReturn(Optional.of(mockSender));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> messageService.saveMessage(inputTO)
        );
        assertTrue(ex.getMessage().contains("Receiver not found for ID: 999"));
        verify(chatRepository).findById(10L);
        verify(userRepository).findById(100L);
        verify(userRepository).findById(999L);
        verifyNoInteractions(messageRepository);
    }

    @Test
    void testMarkAsRead_Success() {
        Long messageId = 1L;
        MessageDBO mockMessage = new MessageDBO();
        mockMessage.setMessageId(messageId);
        mockMessage.setIsRead(false);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(mockMessage));

        messageService.markAsRead(messageId);

        assertTrue(mockMessage.getIsRead());
        assertNotNull(mockMessage.getReadAt());

        verify(messageRepository).findById(messageId);
        verify(messageRepository).save(mockMessage);
    }

    @Test
    void testMarkAsRead_MessageNotFound() {
        Long messageId = 999L;
        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> messageService.markAsRead(messageId)
        );
        assertTrue(ex.getMessage().contains("Message not found for ID: 999"));
        verify(messageRepository).findById(messageId);
        verify(messageRepository, never()).save(any(MessageDBO.class));
    }

    @Test
    void testGetMessagesByChatId() {
        Long chatId = 10L;
        MessageDBO msg1 = new MessageDBO();
        msg1.setMessageId(101L);
        MessageDBO msg2 = new MessageDBO();
        msg2.setMessageId(102L);

        when(messageRepository.findByChat_ChatId(chatId))
                .thenReturn(List.of(msg1, msg2));

        MessageTO dto1 = new MessageTO();
        dto1.setMessageId(101L);

        MessageTO dto2 = new MessageTO();
        dto2.setMessageId(102L);

        when(messageTOMapper.toDTOList(List.of(msg1, msg2)))
                .thenReturn(List.of(dto1, dto2));

        List<MessageTO> result = messageService.getMessagesByChatId(chatId);

        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getMessageId());
        assertEquals(102L, result.get(1).getMessageId());

        verify(messageRepository).findByChat_ChatId(chatId);
        verify(messageTOMapper).toDTOList(List.of(msg1, msg2));
    }
}
