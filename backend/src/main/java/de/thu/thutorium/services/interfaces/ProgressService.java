package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.ProgressTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public interface ProgressService {

    @Transactional
    void createProgress(ProgressTO progressTO);
    boolean deleteProgress(Long studentId, Long courseId);

    boolean updateProgress(Long studentId, Long courseId, Double points);
}
