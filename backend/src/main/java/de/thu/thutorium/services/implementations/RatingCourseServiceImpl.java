package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.TOMappers.RatingCourseTOMapper;
import de.thu.thutorium.api.transferObjects.common.RatingCourseTO;
import de.thu.thutorium.database.dbObjects.RatingCourseDBO;
import de.thu.thutorium.database.repositories.RatingCourseRepository;
import de.thu.thutorium.services.interfaces.RatingCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingCourseServiceImpl implements RatingCourseService {
    private final RatingCourseRepository ratingCourseRepository;
    private final RatingCourseTOMapper ratingCourseTOMapper;

    @Override
    public List<RatingCourseTO> getCourseRatings(Long courseId) {
        List<RatingCourseDBO> ratings = ratingCourseRepository.findByCourse_CourseId(courseId);
        return ratings.stream().map(ratingCourseTOMapper::toDTO).toList();
    }
}
