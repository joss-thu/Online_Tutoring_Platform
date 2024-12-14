package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import org.springframework.stereotype.Service;

@Service
public interface UniversityService {
    UniversityDBO createUniversity(UniversityTO university);
}
