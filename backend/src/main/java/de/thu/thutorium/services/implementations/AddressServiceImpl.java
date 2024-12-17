package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.mappers.common.AddressMapper;
import de.thu.thutorium.api.transferObjects.common.AddressTO;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import de.thu.thutorium.database.exceptions.ResourceAlreadyExistsException;
import de.thu.thutorium.database.repositories.AddressRepository;
import de.thu.thutorium.services.interfaces.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for managing addresses.
 */
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    /**
     * Creates a new address.
     *
     * @param address the {@code AddressTO} object containing the address data
     * @return the created {@code AddressTO} object
     */
    @Override
    public AddressTO createAddress(@Valid AddressTO address) {
        if (addressExists(address)) {
            throw new ResourceAlreadyExistsException(address + " already exists!");
        }
        AddressDBO addressDBO = addressMapper.toDBO(address);
        AddressDBO savedAddressDBO = addressRepository.save(addressDBO);
        return addressMapper.toDTO(savedAddressDBO);
    }

    /**
     * Checks if the address already exists.
     * This method checks if an address with the same house number, street name,
     * postal code, and country already exists
     * in the database, ignoring case differences.
     *
     * @param address the {@code AddressTO} object containing the address data
     * @return {@code true} if the address exists, {@code false} otherwise
     */
    @Override
    public Boolean addressExists(AddressTO address) {
        Optional<AddressDBO> existingAddress = addressRepository.findByHouseNumAndStreetNameAndPostalCodeAndCountryContainsIgnoreCase(
                address.getHouseNum(),
                address.getStreetName(),
                address.getPostalCode(),
                address.getCountry()
        );
        return existingAddress.isPresent();
    }
}
