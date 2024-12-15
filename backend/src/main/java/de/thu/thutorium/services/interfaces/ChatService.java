package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.ChatCreateTO;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
    void createChat(ChatCreateTO requestDTO);
    void deleteChat(Long chatId);}
