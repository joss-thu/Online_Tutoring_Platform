package de.thu.thutorium.contollers;

import de.thu.thutorium.api.controllers.WebSocketController;
import de.thu.thutorium.api.transferObjects.chat.ChatSummaryTO;
import de.thu.thutorium.api.transferObjects.chat.ReceiverTO;
import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import de.thu.thutorium.api.transferObjects.common.MessageTO;
import de.thu.thutorium.services.interfaces.ChatService;
import de.thu.thutorium.services.interfaces.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSocketControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private WebSocketController webSocketController;

    private MessageTO mockMessageTO;
    private ChatCreateTO mockChatCreateTO;
    private ChatSummaryTO mockChatSummaryTO;
    private ReceiverTO mockReceiverTO;

    @BeforeEach
    void setUp() {
        mockMessageTO = new MessageTO();
        mockMessageTO.setMessageId(1L);
        mockMessageTO.setSenderId(1L);
        mockMessageTO.setReceiverId(2L);
        mockMessageTO.setChatId(1L);
        mockMessageTO.setMessageContent("Test message");
        mockMessageTO.setSendAt(LocalDateTime.now());
        mockMessageTO.setReadAt(null);
        mockMessageTO.setIsRead(false);

        mockChatCreateTO = new ChatCreateTO();
        mockChatCreateTO.setParticipantIds(List.of(1L, 2L));
        mockChatCreateTO.setChatTitle("Group Chat");
        mockChatCreateTO.setIsGroup(true);

        mockReceiverTO = new ReceiverTO();
        mockReceiverTO.setId(2L);
        mockReceiverTO.setFirstName("Test");
        mockReceiverTO.setLastName("Receiver");

        mockChatSummaryTO = new ChatSummaryTO();
        mockChatSummaryTO.setChatId(1L);
        mockChatSummaryTO.setReceiver(mockReceiverTO);
        mockChatSummaryTO.setUnreadMessages(5);
    }

    @Test
    void testSendMessage() {
        when(messageService.saveMessage(mockMessageTO)).thenReturn(mockMessageTO);

        MessageTO result = webSocketController.sendMessage(mockMessageTO);

        assertNotNull(result);
        assertEquals("Test message", result.getMessageContent());
        verify(messageService, times(1)).saveMessage(mockMessageTO);
    }

    @Test
    void testPostSendMessage() {
        when(messageService.saveMessage(mockMessageTO)).thenReturn(mockMessageTO);

        ResponseEntity<MessageTO> response = webSocketController.PostsendMessage(mockMessageTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test message", response.getBody().getMessageContent());
        verify(messageService, times(1)).saveMessage(mockMessageTO);
    }

    @Test
    void testCreateChat() {
        doNothing().when(chatService).createChat(mockChatCreateTO);

        ResponseEntity<String> response = webSocketController.createChat(mockChatCreateTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Chat created successfully!", response.getBody());
        verify(chatService, times(1)).createChat(mockChatCreateTO);
    }

    @Test
    void testDeleteChat() {
        doNothing().when(chatService).deleteChat(1L);

        ResponseEntity<String> response = webSocketController.deleteChat(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        assertEquals("Chat deleted successfully!", response.getBody());
        verify(chatService, times(1)).deleteChat(1L);
    }

    @Test
    void testGetChatSummaries() {
        when(chatService.getChatSummaries(1L)).thenReturn(List.of(mockChatSummaryTO));

        ResponseEntity<List<ChatSummaryTO>> response = webSocketController.getChatSummaries(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test", response.getBody().get(0).getReceiver().getFirstName());
        assertEquals("Receiver", response.getBody().get(0).getReceiver().getLastName());
        verify(chatService, times(1)).getChatSummaries(1L);
    }

    @Test
    void testGetChatMessages() {
        when(messageService.getMessagesByChatId(1L)).thenReturn(List.of(mockMessageTO));

        ResponseEntity<List<MessageTO>> response = webSocketController.getChatMessages(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test message", response.getBody().get(0).getMessageContent());
        verify(messageService, times(1)).getMessagesByChatId(1L);
    }

    @Test
    void testMarkMessageAsRead() {
        doNothing().when(messageService).markAsRead(1L);

        ResponseEntity<String> response = webSocketController.markMessageAsRead(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All messages marked as read successfully.", response.getBody());
        verify(messageService, times(1)).markAsRead(1L);
    }
}
