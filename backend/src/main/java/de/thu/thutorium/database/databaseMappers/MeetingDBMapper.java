package de.thu.thutorium.database.databaseMappers;

import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.database.dbObjects.MeetingDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
public class MeetingDBMapper {
    public MeetingDBO toEntity(MeetingTO meetingTO) {
        return MeetingDBO.builder()
                .meetingDate(meetingTO.getMeetingDate())
                .meetingTime(meetingTO.getMeetingTime())
                .duration(meetingTO.getDuration())
                .meetingType(meetingTO.getMeetingType())
                .meetingStatus(meetingTO.getMeetingStatus())
                .build();
    }
}
