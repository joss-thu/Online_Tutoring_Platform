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
 *
 * <p>This service is responsible for handling address-related operations, such as creating new
 * addresses and checking whether an address already exists in the system. It interacts with the
 * {@link AddressRepository} for database operations and the {@link AddressMapper} for mapping
 * between transfer objects and database objects.
 *
 * <p>The service ensures that duplicate addresses are not created by validating if the address
 * already exists before saving it to the database. The existence check compares the house number,
 * street name, postal code, and country.
 */
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressRepository;
  private final AddressMapper addressMapper;

  /**
   * Creates a new address.
   *
   * <p>This method accepts an {@link AddressTO} object, checks if the address already exists, and
   * saves it to the database if it doesn't. The saved address is then mapped to a DTO and returned.
   *
   * @param address the {@code AddressTO} object containing the address data
   * @return the created {@code AddressTO} object
   * @throws ResourceAlreadyExistsException if the address already exists in the system
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
   *
   * <p>This method checks if an address with the same house number, street name, postal code, and
   * country already exists in the database, ignoring case differences. If such an address is found,
   * it returns {@code true}; otherwise, it returns {@code false}.
   *
   * @param address the {@code AddressTO} object containing the address data
   * @return {@code true} if the address exists, {@code false} otherwise
   */
  @Override
  public Boolean addressExists(AddressTO address) {
    Optional<AddressDBO> existingAddress =
        addressRepository.findByHouseNumAndStreetNameAndPostalCodeAndCountryContainsIgnoreCase(
            address.getHouseNum(),
            address.getStreetName(),
            address.getPostalCode(),
            address.getCountry());
    return existingAddress.isPresent();
  }
}
