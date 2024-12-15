package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.AddressTO;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing addresses.
 */
@Service
public interface AddressService {

    /**
     * Creates a new address.
     *
     * @param addressTO the {@code AddressTO} object containing the address data
     * @return the created {@code AddressTO} object
     */
    AddressTO createAddress(AddressTO addressTO);

    /**
     * Checks if the address already exists.
     *
     * @param address the {@code AddressTO} object containing the address data
     * @return the boolean value corresponding to if the address already exists or not.
     */
    Boolean addressExists(AddressTO address);

}
