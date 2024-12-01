package de.thu.thutorium.api.frontendMappers;

import de.thu.thutorium.database.dbObjects.Course;
import de.thu.thutorium.api.transferObjects.CourseDTO;
import org.mapstruct.*;

import java.util.List;

/** A MapStruct mapper for converting between Course entities and CourseDTOs. */
@Mapper(
    componentModel =
        "spring", // means its a spring managed bean, i can inject it using @Autowired in services
                  // or controllers

    //        uses = {UserMapper.class, RatingMapper.class, CategoryMapper.class}, // used for
    // nested mappings , uncomment once you have UserMapper or derived from it

    unmappedTargetPolicy =
        ReportingPolicy
            .IGNORE) // Tells MapStruct to ignore any unmapped fields in the target class without
                     // throwing errors (CourseDTO has field , that doesn't exist in Course)
public interface CourseMapper {

  // For displaying/updating : maps fields between Course and Course DTO
  @Mapping(source = "tutor", target = "tutor")
  @Mapping(source = "ratings", target = "ratings")
  @Mapping(source = "category", target = "categoryId")
  CourseDTO toDTO(Course course);

  // For Creation: maps fields from CourseCreateDTO to Course (Ratings not included, since initially course comes with no ratings)
  @Mapping(source = "tutor", target = "tutor") // Map tutor from DTO
  @Mapping(source = "categoryId", target = "category") // Fetch category by ID in service
  Course toEntity(CourseDTO dto);

  //Used for List Mappings
  List<CourseDTO> toDTOList(List<Course> courses);
}
