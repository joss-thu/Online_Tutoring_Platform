package de.thu.thutorium.api.TOMappers;

import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.database.dbObjects.MeetingDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper interface for converting between {@link MeetingDBO} and {@link MeetingTO}.
 *
 * <p>This interface uses MapStruct to map fields between the entity and transfer object
 * representations of a meeting. The mapping is bidirectional, enabling conversion from a database
 * object to a transfer object for API responses.
 *
 * <p>The mapper is configured to be a Spring component using {@code componentModel = "spring"}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
      AddressTOMapper.class,
      UniversityTOMapper.class,
    })
public interface MeetingTOMapper {

  /**
   * Converts a {@link MeetingDBO} entity to a {@link MeetingTO} transfer object.
   *
   * @param meetingDBO the {@link MeetingDBO} entity to convert
   * @return a {@link MeetingTO} transfer object with mapped fields
   */
  @Mappings({
    @Mapping(source = "tutor.userId", target = "tutorId"),
    @Mapping(source = "course.courseId", target = "courseId"),
    @Mapping(source = "meetingDate", target = "meetingDate"),
    @Mapping(source = "startTime", target = "startTime"),
    @Mapping(source = "meetingLink", target = "meetingLink"),
    @Mapping(source = "endTime", target = "endTime"),
    @Mapping(source = "duration", target = "duration"),
    @Mapping(source = "meetingType", target = "meetingType"),
    @Mapping(source = "roomNum", target = "roomNum"),
    @Mapping(source = "address.addressId", target = "addressId"),
    @Mapping(source = "address.campusName", target = "campusName"),
    @Mapping(source = "address.university.universityName", target = "universityName"),
  })
  MeetingTO toDTO(MeetingDBO meetingDBO);

  /**
   * Converts a list of {@link MeetingDBO} entities to a list of {@link MeetingTO} transfer objects.
   *
   * @param meetingDBOs the list of {@link MeetingDBO} entities to convert
   * @return a list of {@link MeetingTO} transfer objects with mapped fields
   */
  List<MeetingTO> toDTOList(List<MeetingDBO> meetingDBOs);
}
