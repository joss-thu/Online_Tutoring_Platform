package de.thu.thutorium.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    private UserDBO createUser(Long id, String firstName, String lastName) {
        return UserDBO.builder()
                .userId(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com")
                .password("password123")
                .isVerified(true)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void saveMessage_ShouldSaveMessageSuccessfully() {
        Long chatId = 1L;
        Long senderId = 2L;
        Long receiverId = 3L;

        ChatDBO chatDBO = new ChatDBO();
        chatDBO.setChatId(chatId);

        UserDBO senderDBO = createUser(senderId, "Sender", "User");
        UserDBO receiverDBO = createUser(receiverId, "Receiver", "User");

        MessageTO messageTO = new MessageTO(null, senderId, receiverId, chatId, "Hello", LocalDateTime.now(), null, false);
        MessageDBO messageDBO = MessageDBO.builder()
                .chat(chatDBO)
                .sender(senderDBO)
                .receiver(receiverDBO)
                .messageContent("Hello")
                .sendAt(LocalDateTime.now())
                .isRead(false)
                .build();

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chatDBO));
        when(userRepository.findById(senderId)).thenReturn(Optional.of(senderDBO));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiverDBO));
        when(messageRepository.save(any(MessageDBO.class))).thenReturn(messageDBO);

        MessageTO savedMessage = messageService.saveMessage(messageTO);

        assertNotNull(savedMessage);
        assertEquals(senderId, savedMessage.getSenderId());
        assertEquals(receiverId, savedMessage.getReceiverId());
        assertEquals(chatId, savedMessage.getChatId());
        assertEquals("Hello", savedMessage.getMessageContent());
    }

    @Test
    void markAsRead_ShouldMarkMessagesAsRead() {
        Long chatId = 1L;
        MessageDBO message1 = MessageDBO.builder().messageId(1L).isRead(false).build();
        MessageDBO message2 = MessageDBO.builder().messageId(2L).isRead(false).build();

        when(messageRepository.findAllByChatIdAndIsReadFalse(chatId)).thenReturn(Arrays.asList(message1, message2));

        messageService.markAsRead(chatId);

        assertTrue(message1.getIsRead());
        assertTrue(message2.getIsRead());
        verify(messageRepository, times(1)).saveAll(Arrays.asList(message1, message2));
    }

    @Test
    void getMessagesByChatId_ShouldReturnMessages() {
        Long chatId = 1L;
        MessageDBO messageDBO1 = MessageDBO.builder().messageId(1L).messageContent("Hi").build();
        MessageDBO messageDBO2 = MessageDBO.builder().messageId(2L).messageContent("Hello").build();
        List<MessageDBO> messageDBOs = Arrays.asList(messageDBO1, messageDBO2);

        MessageTO messageTO1 = new MessageTO(1L, 2L, 3L, chatId, "Hi", LocalDateTime.now(), null, false);
        MessageTO messageTO2 = new MessageTO(2L, 3L, 2L, chatId, "Hello", LocalDateTime.now(), null, false);
        List<MessageTO> messageTOs = Arrays.asList(messageTO1, messageTO2);

        when(messageRepository.findByChat_ChatId(chatId)).thenReturn(messageDBOs);
        when(messageTOMapper.toDTOList(messageDBOs)).thenReturn(messageTOs);

        List<MessageTO> result = messageService.getMessagesByChatId(chatId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hi", result.get(0).getMessageContent());
        assertEquals("Hello", result.get(1).getMessageContent());
    }
}
