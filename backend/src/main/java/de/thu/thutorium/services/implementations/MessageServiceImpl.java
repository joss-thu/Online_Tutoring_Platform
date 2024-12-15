package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.transferObjects.common.MessageTO;
import de.thu.thutorium.database.dbObjects.ChatDBO;
import de.thu.thutorium.database.dbObjects.MessageDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.ChatRepository;
import de.thu.thutorium.database.repositories.MessageRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.interfaces.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(
            MessageRepository messageRepository,
            ChatRepository chatRepository,
            UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public MessageTO saveMessage(MessageTO messageTO) {
        // Validate input data
        if (messageTO == null) {
            throw new IllegalArgumentException("MessageTO cannot be null");
        }

        // Find the chat by ID (assuming messageTO contains a chat ID)
        ChatDBO chat = chatRepository
                .findById(messageTO.getChatId()) // Corrected to use chatId from MessageTO
                .orElseThrow(() -> new RuntimeException("Chat not found for ID: " + messageTO.getChatId()));

        // Find the sender by ID
        UserDBO sender = userRepository
                .findById(messageTO.getSenderId()) // Use senderId from MessageTO
                .orElseThrow(() -> new RuntimeException("Sender not found for ID: " + messageTO.getSenderId()));

        // Find the receiver by ID
        UserDBO receiver = userRepository
                .findById(messageTO.getReceiverId()) // Use receiverId from MessageTO
                .orElseThrow(() -> new RuntimeException("Receiver not found for ID: " + messageTO.getReceiverId()));

        // Create a new message entity
        MessageDBO messageDBO = MessageDBO.builder()
                .chat(chat)
                .sender(sender)
                .receiver(receiver)
                .messageContent(messageTO.getMessageContent())
                .sendAt(messageTO.getSendAt()) // Use sendAt from MessageTO
                .isRead(false)
                .build();

        // Save the message to the database
        messageRepository.save(messageDBO);

        // Map the saved entity back to a DTO and return it
        return new MessageTO(
                messageDBO.getMessageId(),
                messageDBO.getSender().getUserId(),
                messageDBO.getReceiver().getUserId(),
                messageDBO.getChat().getChatId(), // Return the chatId from the saved message
                messageDBO.getMessageContent(),
                messageDBO.getSendAt(),
                messageDBO.getReadAt(),
                messageDBO.getIsRead()
        );
    }

    @Override
    public MessageTO markAsRead(Long messageId) {
        // Find the message by ID
        MessageDBO messageDBO = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found for ID: " + messageId));

        // Set the message as read
        messageDBO.setIsRead(true);
        messageDBO.setReadAt(LocalDateTime.now());

        // Save the updated message
        messageRepository.save(messageDBO);

        // Map the updated entity back to a DTO and return it
        return new MessageTO(
                messageDBO.getMessageId(),
                messageDBO.getSender().getUserId(), // Return the senderId
                messageDBO.getReceiver().getUserId(), // Return the receiverId
                messageDBO.getChat().getChatId(), // Return the chatId from the saved message
                messageDBO.getMessageContent(),
                messageDBO.getSendAt(),
                messageDBO.getReadAt(),
                messageDBO.getIsRead()
        );
    }
}
