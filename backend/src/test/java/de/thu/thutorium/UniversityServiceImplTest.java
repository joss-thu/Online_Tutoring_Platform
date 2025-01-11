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
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversityServiceImplTest {

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private UniversityTOMapper universityTOMapper;

    @Mock
    private UniversityDBOMapper universityDBOMapper;

    @InjectMocks
    private UniversityServiceImpl universityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUniversity_Success() {
        UniversityTO inputTO = new UniversityTO();
        inputTO.setUniversityName("Test University");

        UniversityDBO dbObject = new UniversityDBO("Test University");

        when(universityRepository.findByUniversityName("Test University"))
                .thenReturn(Optional.empty());

        when(universityDBOMapper.toDBO(inputTO)).thenReturn(dbObject);

        UniversityDBO savedDBO = new UniversityDBO("Test University");
        savedDBO = spy(savedDBO);
        doReturn(10L).when(savedDBO).getUniversityId();

        when(universityRepository.save(dbObject)).thenReturn(savedDBO);

        UniversityTO outputTO = new UniversityTO();
        outputTO.setUniversityName("Test University");
        when(universityTOMapper.toDTO(savedDBO)).thenReturn(outputTO);

        UniversityTO result = universityService.createUniversity(inputTO);

        assertNotNull(result);
        assertEquals("Test University", result.getUniversityName());
        verify(universityRepository, times(1)).findByUniversityName("Test University");
        verify(universityRepository, times(1)).save(dbObject);
        verify(universityDBOMapper, times(1)).toDBO(inputTO);
        verify(universityTOMapper, times(1)).toDTO(savedDBO);
    }

    @Test
    void testCreateUniversity_AlreadyExists() {
        UniversityTO inputTO = new UniversityTO();
        inputTO.setUniversityName("Existing University");

        UniversityDBO existingDBO = new UniversityDBO("Existing University");

        when(universityRepository.findByUniversityName("Existing University"))
                .thenReturn(Optional.of(existingDBO));

        assertThrows(
                ResourceAlreadyExistsException.class,
                () -> universityService.createUniversity(inputTO),
                "Expected an exception when the university already exists"
        );

        verify(universityRepository, times(1)).findByUniversityName("Existing University");
        verify(universityRepository, never()).save(any(UniversityDBO.class));
        verify(universityDBOMapper, never()).toDBO(any(UniversityTO.class));
        verify(universityTOMapper, never()).toDTO(any(UniversityDBO.class));
    }

    @Test
    void testUniversityExists_True() {
        UniversityTO inputTO = new UniversityTO();
        inputTO.setUniversityName("Some University");

        UniversityDBO foundDBO = new UniversityDBO("Some University");
        when(universityRepository.findByUniversityName("Some University"))
                .thenReturn(Optional.of(foundDBO));

        Boolean exists = universityService.universityExists(inputTO);

        assertTrue(exists);
        verify(universityRepository).findByUniversityName("Some University");
    }

    @Test
    void testUniversityExists_False() {
        UniversityTO inputTO = new UniversityTO();
        inputTO.setUniversityName("Nonexistent University");

        when(universityRepository.findByUniversityName("Nonexistent University"))
                .thenReturn(Optional.empty());

        Boolean exists = universityService.universityExists(inputTO);

        assertFalse(exists);
        verify(universityRepository).findByUniversityName("Nonexistent University");
    }
}
