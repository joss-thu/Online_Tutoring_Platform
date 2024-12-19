package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.AddressTO;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing addresses.
 *
 * <p>This interface defines methods for creating a new address and checking if an address already
 * exists in the system. The {@link AddressTO} transfer object is used for address data
 * representation and interaction with the service layer.
 */
@Service
public interface AddressService {

  /**
   * Creates a new address.
   *
   * <p>This method takes an {@link AddressTO} object containing the address data and returns the
   * created {@link AddressTO} object. It is expected that the implementation of this interface will
   * handle the creation of a new address entity in the system.
   *
   * @param addressTO the {@code AddressTO} object containing the address data.
   * @return the created {@code AddressTO} object representing the newly created address.
   */
  AddressTO createAddress(AddressTO addressTO);

  /**
   * Checks if the address already exists.
   *
   * <p>This method checks if the provided address already exists in the system. It compares the
   * provided {@link AddressTO} with existing addresses to determine if a match is found. If an
   * address with the same data exists, the method will return {@code true}, otherwise {@code
   * false}.
   *
   * @param address the {@code AddressTO} object containing the address data to check.
   * @return {@code true} if the address already exists, otherwise {@code false}.
   */
  Boolean addressExists(AddressTO address);
}
