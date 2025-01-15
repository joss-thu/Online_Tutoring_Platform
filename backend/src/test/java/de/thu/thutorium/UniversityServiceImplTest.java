package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.UniversityTOMapper;
import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.DBOMappers.UniversityDBOMapper;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import de.thu.thutorium.database.repositories.UniversityRepository;
import de.thu.thutorium.exceptions.ResourceAlreadyExistsException;
import de.thu.thutorium.services.implementations.UniversityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityServiceImplTest {

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private UniversityTOMapper universityTOMapper;

    @Mock
    private UniversityDBOMapper universityDBOMapper;

    @InjectMocks
    private UniversityServiceImpl universityService;

    private UniversityTO universityTO;
    private UniversityDBO universityDBO;

    @BeforeEach
    void setUp() {
        universityTO = new UniversityTO();
        universityTO.setUniversityName("Test University");

        universityDBO = new UniversityDBO();
        universityDBO.setUniversityName("Test University");
    }

    @Test
    void createUniversity_ShouldCreateUniversity_WhenUniversityDoesNotExist() {
        when(universityRepository.findByUniversityName("Test University")).thenReturn(Optional.empty());
        when(universityDBOMapper.toDBO(universityTO)).thenReturn(universityDBO);
        when(universityTOMapper.toDTO(universityDBO)).thenReturn(universityTO);

        UniversityTO result = universityService.createUniversity(universityTO);

        assertNotNull(result);
        assertEquals("Test University", result.getUniversityName());
        verify(universityRepository, times(1)).save(universityDBO);
    }

    @Test
    void createUniversity_ShouldThrowException_WhenUniversityAlreadyExists() {
        when(universityRepository.findByUniversityName("Test University")).thenReturn(Optional.of(universityDBO));

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> universityService.createUniversity(universityTO)
        );

        assertEquals("University with name Test University already exists", exception.getMessage());
        verify(universityRepository, never()).save(any(UniversityDBO.class));
    }

    @Test
    void universityExists_ShouldReturnTrue_WhenUniversityExists() {
        when(universityRepository.findByUniversityName("Test University")).thenReturn(Optional.of(universityDBO));

        Boolean result = universityService.universityExists(universityTO);

        assertTrue(result);
    }

    @Test
    void universityExists_ShouldReturnFalse_WhenUniversityDoesNotExist() {
        when(universityRepository.findByUniversityName("Test University")).thenReturn(Optional.empty());

        Boolean result = universityService.universityExists(universityTO);

        assertFalse(result);
    }
}
