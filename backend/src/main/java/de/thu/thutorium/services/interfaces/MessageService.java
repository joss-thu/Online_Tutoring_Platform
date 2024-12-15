package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.MessageTO;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
  MessageTO saveMessage(MessageTO messageTO);

  MessageTO markAsRead(Long messageId);
}
