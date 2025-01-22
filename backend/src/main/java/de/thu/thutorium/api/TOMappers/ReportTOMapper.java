package de.thu.thutorium.api.TOMappers;

import de.thu.thutorium.api.transferObjects.common.ReportTO;
import de.thu.thutorium.database.dbObjects.ReportDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportTOMapper {

    /**
     * Converts a {@link de.thu.thutorium.database.dbObjects.ReportDBO} object to a {@link de.thu.thutorium.api.transferObjects.common.ReportTO} object.
     *
     * @param reportDBO the {@code ReportDBO} object to convert
     * @return a {@code ReportTO} object containing the mapped data
     */
    @Mapping(target = "tutorId", source = "tutor.userId")
    @Mapping(target = "tutorFullName", expression = "java(reportDBO.getTutor().getFirstName() + \" \" + reportDBO.getTutor().getLastName())")
    @Mapping(target = "studentId", source = "student.userId")
    @Mapping(target = "studentFullName", expression = "java(reportDBO.getStudent().getFirstName() + \" \" + reportDBO.getStudent().getLastName())")
    @Mapping(target = "courseId", source = "course.courseId")
    @Mapping(target = "courseName", source = "course.courseName")
    @Mapping(target = "meetingId", source = "meeting.meetingId")
    ReportTO toDTO(ReportDBO reportDBO);
}
