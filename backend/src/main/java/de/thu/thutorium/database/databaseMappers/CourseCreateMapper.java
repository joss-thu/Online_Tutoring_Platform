package de.thu.thutorium.database.databaseMappers;

import de.thu.thutorium.api.transferObjects.CourseDTO;
import de.thu.thutorium.database.dbObjects.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseCreateMapper {
    // For Creation: maps fields from CourseCreateDTO to Course (Ratings not included, since initially course comes with no ratings)
    @Mapping(source = "tutor", target = "tutor") // Map tutor from DTO
    @Mapping(source = "categoryId", target = "category") // Fetch category by ID in service
    Course toEntity(CourseDTO dto);
}
