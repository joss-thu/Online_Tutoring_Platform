package de.thu.thutorium.api.frontendMappers;

import de.thu.thutorium.api.transferObjects.TutorDTO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * A MapStruct mapper interface for converting between {@link UserDBO} (User Database Object) and
 * {@link TutorDTO} (Tutor Data Transfer Object).
 *
 * <p>This interface defines the mapping logic to convert a {@code UserDBO} (representing a tutor)
 * to a {@code TutorDTO}. It uses MapStruct to automate the conversion process, making the mapping
 * type-safe and efficient.
 *
 * <p>The {@code uses} attribute specifies other mappers that will be used for mapping related
 * objects, including:
 *
 * <ul>
 *   <li>{@link CourseMapper} for mapping courses associated with the tutor
 *   <li>{@link RatingTutorMapper} for mapping ratings associated with the tutor
 *   <li>{@link UserMapper} for mapping general user data to the base DTO
 * </ul>
 *
 * <p>Note: The {@code componentModel = "spring"} annotation indicates that MapStruct will generate
 * a Spring bean for this mapper, allowing it to be injected into Spring components or services.
 */
@Mapper(
    componentModel = "spring",
    uses = {CourseMapper.class, RatingTutorMapper.class, UserMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TutorMapper {
  /**
   * Converts a {@link UserDBO} (representing a tutor) to a {@link TutorDTO}.
   *
   * @param tutor the {@code UserDBO} object representing the tutor to convert
   * @return a {@code TutorDTO} object containing the tutor's data
   */
  @Mapping(target = "tutorCourses", source = "tutorCourses") // Map Set<CourseDBO> to List<CourseDTO>
  TutorDTO toDTO(UserDBO tutor);

  List<TutorDTO> toDTOList(List<UserDBO> users);
}
