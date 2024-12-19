package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.mappers.common.AddressMapper;
import de.thu.thutorium.api.mappers.common.UniversityMapper;
import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import de.thu.thutorium.database.exceptions.ResourceAlreadyExistsException;
import de.thu.thutorium.database.repositories.AddressRepository;
import de.thu.thutorium.database.repositories.UniversityRepository;
import de.thu.thutorium.services.interfaces.UniversityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for managing universities. This class provides methods for creating and
 * managing university entities. It uses {@link UniversityRepository} for database operations and
 * {@link UniversityMapper} for mapping between transfer objects and database objects.
 *
 * <p>The service handles the creation of universities and checks if a university already exists
 * based on its name. It also utilizes {@link AddressServiceImpl} and {@link AddressMapper} to
 * manage and map the address of the university.
 */
@RequiredArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {

  private final UniversityRepository universityRepository;
  private final AddressRepository addressRepository;
  private final UniversityMapper universityMapper;
  private final AddressServiceImpl addressService;
  private final AddressMapper addressMapper;

  /**
   * Creates a new university.
   *
   * <p>This method first checks if the university already exists by calling {@link
   * #universityExists(UniversityTO)}. If the university exists, it throws a {@link
   * ResourceAlreadyExistsException}. Otherwise, it maps the given {@link UniversityTO} to a {@link
   * UniversityDBO}, saves it to the database, and returns the corresponding {@link UniversityTO}.
   *
   * @param university the {@link UniversityTO} object containing the data for the new university
   * @return the {@link UniversityTO} object representing the created university
   * @throws ResourceAlreadyExistsException if a university with the same name already exists
   */
  @Override
  @Transactional
  public UniversityTO createUniversity(UniversityTO university) {
    if (universityExists(university)) {
      throw new ResourceAlreadyExistsException(
          "University with name " + university.getUniversityName() + " already exists");
    }
    UniversityDBO universityDBO = universityMapper.toDBO(university);
    universityRepository.save(universityDBO);
    return universityMapper.toDTO(universityDBO);
  }

  /**
   * Checks if the university already exists in the database.
   *
   * <p>This method checks the {@link UniversityRepository} to find a university with the same name
   * as the one provided in the {@link UniversityTO} object. It returns {@code true} if the
   * university exists, and {@code false} otherwise.
   *
   * @param university the {@link UniversityTO} object containing the university data
   * @return {@code true} if a university with the same name already exists, otherwise {@code false}
   */
  @Override
  public Boolean universityExists(UniversityTO university) {
    Optional<UniversityDBO> existingUniversity =
        universityRepository.findByUniversityName(university.getUniversityName());
    return existingUniversity.isPresent();
  }
}
