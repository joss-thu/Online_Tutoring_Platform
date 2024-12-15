package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import de.thu.thutorium.database.databaseMappers.ChatDBMapper;
import de.thu.thutorium.database.dbObjects.ChatDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.ChatRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.interfaces.ChatService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatDBMapper chatMapper;

    public void createChat(ChatCreateTO requestDTO) {
        // Fetch participants from the database
        Set<UserDBO> participants = new HashSet<>(
                userRepository.findAllById(requestDTO.getParticipantIds())
        );

        // Ensure all participants are valid
        if (participants.size() != requestDTO.getParticipantIds().size()) {
            throw new EntityNotFoundException("One or more participants not found.");
        }

        // Map DTO to Entity
        ChatDBO chatDBO = chatMapper.toEntity(requestDTO, participants);

        // Save Chat Entity
        chatRepository.save(chatDBO);
    }
}
