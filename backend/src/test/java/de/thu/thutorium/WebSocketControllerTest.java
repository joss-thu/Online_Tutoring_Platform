package de.thu.thutorium;

import de.thu.thutorium.api.controllers.WebSocketController;
import de.thu.thutorium.api.transferObjects.common.MessageTO;
import de.thu.thutorium.api.transferObjects.chat.ChatSummaryTO;
import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import de.thu.thutorium.services.interfaces.ChatService;
import de.thu.thutorium.services.interfaces.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WebSocketControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private WebSocketController webSocketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWebSocketControllerMethods() {
        // Create a sample MessageTO object
        MessageTO messageTO = new MessageTO();
        messageTO.setMessageContent("Test Message");
        messageTO.setSenderId(1L);
        messageTO.setReceiverId(2L);
        messageTO.setChatId(3L);
        messageTO.setSendAt(LocalDateTime.now());
        messageTO.setIsRead(false);

        // Test sendMessage
        when(messageService.saveMessage(messageTO)).thenReturn(messageTO);

        MessageTO sendMessageResult = webSocketController.sendMessage(messageTO);
        assertEquals("Test Message", sendMessageResult.getMessageContent());
        verify(messageService, times(1)).saveMessage(messageTO);

        // Test PostsendMessage
        ResponseEntity<MessageTO> postsendMessageResponse = webSocketController.PostsendMessage(messageTO);
        assertEquals(200, postsendMessageResponse.getStatusCodeValue());
        assertEquals("Test Message", postsendMessageResponse.getBody().getMessageContent());
        verify(messageService, times(2)).saveMessage(messageTO); // Called twice now

        // Test createChat
        ChatCreateTO chatCreateTO = new ChatCreateTO();
        ResponseEntity<String> createChatResponse = webSocketController.createChat(chatCreateTO);
        assertEquals(201, createChatResponse.getStatusCodeValue());
        assertEquals("Chat created successfully!", createChatResponse.getBody());
        verify(chatService, times(1)).createChat(chatCreateTO);

        // Test deleteChat
        Long chatId = 1L;
        ResponseEntity<String> deleteChatResponse = webSocketController.deleteChat(chatId);
        assertEquals(204, deleteChatResponse.getStatusCodeValue());
        assertEquals("Chat deleted successfully!", deleteChatResponse.getBody());
        verify(chatService, times(1)).deleteChat(chatId);

        // Test getChatSummaries
        Long userId = 1L;
        List<ChatSummaryTO> summaries = Arrays.asList(new ChatSummaryTO());
        when(chatService.getChatSummaries(userId)).thenReturn(summaries);

        ResponseEntity<List<ChatSummaryTO>> chatSummariesResponse = webSocketController.getChatSummaries(userId);
        assertEquals(200, chatSummariesResponse.getStatusCodeValue());
        assertEquals(summaries, chatSummariesResponse.getBody());
        verify(chatService, times(1)).getChatSummaries(userId);

        // Test getChatMessages
        List<MessageTO> messages = Arrays.asList(new MessageTO());
        when(messageService.getMessagesByChatId(chatId)).thenReturn(messages);

        ResponseEntity<List<MessageTO>> chatMessagesResponse = webSocketController.getChatMessages(chatId);
        assertEquals(200, chatMessagesResponse.getStatusCodeValue());
        assertEquals(messages, chatMessagesResponse.getBody());
        verify(messageService, times(1)).getMessagesByChatId(chatId);

        // Test markMessageAsRead
        Long messageId = 1L;
        ResponseEntity<String> markMessageAsReadResponse = webSocketController.markMessageAsRead(messageId);
        assertEquals(200, markMessageAsReadResponse.getStatusCodeValue());
        assertEquals("Message marked as read successfully.", markMessageAsReadResponse.getBody());
        verify(messageService, times(1)).markAsRead(messageId);
    }

}
