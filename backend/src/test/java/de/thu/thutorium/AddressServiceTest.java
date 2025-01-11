package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.AddressTOMapper;
import de.thu.thutorium.api.transferObjects.common.AddressTO;
import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.DBOMappers.AddressDBOMapper;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import de.thu.thutorium.database.repositories.AddressRepository;
import de.thu.thutorium.database.repositories.UniversityRepository;
import de.thu.thutorium.exceptions.ResourceAlreadyExistsException;
import de.thu.thutorium.services.implementations.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressTOMapper addressTOMapper;

    @Mock
    private AddressDBOMapper addressDBOMapper;

    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private AddressTO addressTO;
    private AddressDBO addressDBO;
    private UniversityDBO universityDBO;

    @BeforeEach
    void setUp() {
        UniversityTO universityTO = new UniversityTO();
        addressTO = new AddressTO();

        universityDBO = new UniversityDBO("Test University");
        addressDBO = new AddressDBO();
    }

    @Test
    void createUniversityAndAddress_Success() {
        // Mock repository and mapper behavior
        when(universityRepository.findByUniversityName(any())).thenReturn(Optional.of(universityDBO));
        when(addressRepository.findByHouseNumAndStreetNameIgnoreCaseAndPostalCodeAndCountryIgnoreCaseAndUniversity_UniversityNameIgnoreCase(
                any(), any(), any(), any(), any())).thenReturn(Optional.empty());
        when(addressDBOMapper.toDBO(addressTO)).thenReturn(addressDBO);
        when(addressRepository.save(any(AddressDBO.class))).thenReturn(addressDBO);
        when(addressTOMapper.toDTO(addressDBO)).thenReturn(addressTO);

        // Call the service method
        AddressTO result = addressService.createUniversityAndAddress(addressTO);

        // Verify and assert
        assertNotNull(result);
        assertEquals(addressTO.getHouseNum(), result.getHouseNum());
        assertEquals(addressTO.getStreetName(), result.getStreetName());
        verify(universityRepository, times(1)).findByUniversityName(any());
        verify(addressRepository, times(1)).save(any(AddressDBO.class));
    }

    @Test
    void createUniversityAndAddress_UniversityDoesNotExist_Success() {
        // Mock repository and mapper behavior
        when(universityRepository.findByUniversityName(any())).thenReturn(Optional.empty());
        when(addressRepository.findByHouseNumAndStreetNameIgnoreCaseAndPostalCodeAndCountryIgnoreCaseAndUniversity_UniversityNameIgnoreCase(
                any(), any(), any(), any(), any())).thenReturn(Optional.empty());
        when(addressDBOMapper.toDBO(addressTO)).thenReturn(addressDBO);
        when(addressRepository.save(any(AddressDBO.class))).thenReturn(addressDBO);
        when(addressTOMapper.toDTO(addressDBO)).thenReturn(addressTO);

        // Call the service method
        AddressTO result = addressService.createUniversityAndAddress(addressTO);

        // Verify and assert
        assertNotNull(result);
        assertEquals(addressTO.getUniversity().getUniversityName(), result.getUniversity().getUniversityName());
        verify(universityRepository, times(1)).findByUniversityName(any());
        verify(universityRepository, never()).save(any(UniversityDBO.class));
        verify(addressRepository, times(1)).save(any(AddressDBO.class));
    }

    @Test
    void createUniversityAndAddress_AlreadyExists_ThrowsException() {
        // Mock repository behavior
        when(universityRepository.findByUniversityName(any())).thenReturn(Optional.of(universityDBO));
        when(addressRepository.findByHouseNumAndStreetNameIgnoreCaseAndPostalCodeAndCountryIgnoreCaseAndUniversity_UniversityNameIgnoreCase(
                any(), any(), any(), any(), any())).thenReturn(Optional.of(addressDBO));

        // Assert exception
        assertThrows(ResourceAlreadyExistsException.class, () -> addressService.createUniversityAndAddress(addressTO));

        // Verify no address was saved
        verify(addressRepository, never()).save(any(AddressDBO.class));
    }
}
