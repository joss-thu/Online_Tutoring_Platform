package de.thu.thutorium.database.DBOMappers;



import de.thu.thutorium.api.transferObjects.common.ReportTO;
import de.thu.thutorium.database.dbObjects.ReportDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportDBOMapper {

    /**
     * Converts a {@link ReportTO} object to a {@link ReportDBO} object.
     *
     * @param reportTO the {@code ReportTO} object to convert
     * @return a {@code ReportDBO} object containing the mapped data
     */
    @Mapping(target = "tutor.userId", source = "tutorId")
    @Mapping(target = "student.userId", source = "studentId")
    @Mapping(target = "course.courseId", source = "courseId")
    @Mapping(target = "meeting.meetingId", source = "meetingId")
    @Mapping(target = "text", source = "text")
    ReportDBO toDBO(ReportTO reportTO);
}

