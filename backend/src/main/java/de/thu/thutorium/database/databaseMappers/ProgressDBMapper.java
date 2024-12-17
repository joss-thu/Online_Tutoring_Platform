package de.thu.thutorium.database.databaseMappers;

import de.thu.thutorium.api.transferObjects.common.ProgressTO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.ProgressDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProgressDBMapper {
    /**
     * Map ProgressTO to ProgressDBO.
     *
     * @param progressTO Transfer object for progress
     * @param student UserDBO instance
     * @param course CourseDBO instance
     * @return Mapped ProgressDBO
     */
    @Mapping(target = "progressId", ignore = true) // Ignoring ID as it's generated
    @Mapping(target = "student", source = "student")
    @Mapping(target = "course", source = "course")
    ProgressDBO toEntity(ProgressTO progressTO, UserDBO student, CourseDBO course);
}
