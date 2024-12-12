package de.thu.thutorium.api.frontendMappers;

import de.thu.thutorium.api.transferObjects.CourseDTO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import org.mapstruct.*;

import java.util.List;

/**
 * A MapStruct mapper interface for converting between {@link CourseDBO} (Course Database Object)
 * and {@link de.thu.thutorium.api.transferObjects.CourseDTO} (Course Data Transfer Object).
 *
 * <p>This interface defines the mapping logic to convert a {@code CourseDBO} (representing a course
 * in the database) to a {@code CourseDTO} and vice versa. MapStruct automates the mapping process,
 * ensuring type-safe and efficient conversion between these two objects.
 *
 * <p>The {@code uses} attribute specifies that the {@link UserMapper} and {@link
 * CourseCategoryMapper} will be used to map the {@code createdBy} field (which is a {@code
 * UserDBO}) to a {@code UserBaseDTO} in the {@code CourseDTO}, and the {@code category} field
 * (which is a {@code CourseCategoryDBO}) to a {@code CourseCategoryDTO} in the {@code CourseDTO}.
 *
 * <p>Note: The {@code componentModel = "spring"} annotation indicates that MapStruct will generate
 * a Spring bean for this mapper, making it available for dependency injection in Spring components
 * or services.
 */
@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class, CourseCategoryMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

  /**
   * Maps a single CourseDBO to a CourseDTO.
   *
   * @param course The CourseDBO instance to map.
   * @return The mapped CourseDTO.
   */
  @Mapping(target = "courseCategories", source = "courseCategories") // Map course categories
  @Mapping(target = "receivedCourseRatings", source = "receivedCourseRatings") // Map received course ratings
  @Mapping(target = "tutorId", source = "tutor.userId") // Map tutor ID
  CourseDTO toDTO(CourseDBO course);

  /**
   * Maps a list of CourseDBO instances to a list of CourseDTOs.
   *
   * @param courses The list of CourseDBO instances to map.
   * @return The mapped list of CourseDTOs.
   */
  List<CourseDTO> toDTOList(List<CourseDBO> courses);
}
