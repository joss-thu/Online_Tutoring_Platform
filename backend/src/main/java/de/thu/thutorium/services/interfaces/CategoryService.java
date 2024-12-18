package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.CourseCategoryTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CourseCategoryTO createCourseCategory(CourseCategoryTO courseCategoryTO);
}
