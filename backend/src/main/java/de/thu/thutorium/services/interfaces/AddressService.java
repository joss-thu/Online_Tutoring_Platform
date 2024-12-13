package de.thu.thutorium.services.interfaces;

import de.thu.thutorium.api.transferObjects.common.AddressTO;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    AddressDBO create(AddressTO address);
}
