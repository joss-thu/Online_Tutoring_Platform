package de.thu.thutorium.services.interfaces;
import de.thu.thutorium.api.transferObjects.common.RatingCourseTO;

import java.util.List;

public interface RatingCourseService {
    List<RatingCourseTO> getCourseRatings(Long courseId);
}
