package de.thu.thutorium.database.databaseMappers;

import de.thu.thutorium.api.transferObjects.common.CourseTO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseDBMapper {

    @Mapping(target = "tutor", ignore = true)  // Will be set in the service layer
    @Mapping(target = "students", ignore = true) // Default initialized in the constructor
    @Mapping(target = "courseId", ignore = true) // Auto-generated in the database
    CourseDBO toEntity(CourseTO courseTO);
}
