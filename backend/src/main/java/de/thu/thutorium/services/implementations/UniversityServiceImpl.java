package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.mappers.common.UniversityMapper;
import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import de.thu.thutorium.database.repositories.UniversityRepository;
import de.thu.thutorium.services.interfaces.UniversityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing universities.
 * This class provides methods for creating and managing university entities.
 * It uses {@link UniversityRepository} for database operations and
 * {@link UniversityMapper} for mapping between
 * transfer objects and database objects.
 */
@RequiredArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;
    private final UniversityMapper universitymapper;

    /**
     * Creates a new university.
     * This method converts the {@link UniversityTO} object to a {@link UniversityDBO} object, saves it to the database.
     *
     * @param university the {@code UniversityTO} object containing the university data
     * @return the created {@code UniversityTO} object
     */
    @Override
    @Transactional
    public UniversityTO createUniversity(UniversityTO university) {
        UniversityDBO universityDBO = universitymapper.toDBO(university);
        universityRepository.save(universityDBO);
        return universitymapper.toDTO(universityDBO);
    }
}
