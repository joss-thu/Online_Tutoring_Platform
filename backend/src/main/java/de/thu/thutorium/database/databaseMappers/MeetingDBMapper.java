package de.thu.thutorium.database.databaseMappers;

import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.database.dbObjects.MeetingDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetingDBMapper {
    @Mapping(target = "tutor", ignore = true)  // Will be set in service layer
    @Mapping(target = "course", ignore = true) // Will be set in service layer
    @Mapping(target = "address", ignore = true) // Will be set in service layer
    @Mapping(target = "participants", ignore = true) // Default initialized in the constructor
    @Mapping(target = "meetingId", ignore = true) // Auto-generated in the database
    MeetingDBO toEntity(MeetingTO meetingTO);
}
