package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import org.springframework.stereotype.Service;

@Service
public interface MeetingService {
    void createMeeting(MeetingTO meetingTO);
    void deleteMeeting(Long meetingId);
}
